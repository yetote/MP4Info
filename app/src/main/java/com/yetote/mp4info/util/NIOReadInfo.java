package com.yetote.mp4info.util;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.DataModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NIOReadInfo {
    private static ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.nativeOrder());
    private static List<Box> boxList = new ArrayList<>();
    private static final String TAG = "Read";
    static FileInputStream inputStream;
    static FileChannel fileChannel;
    private static int id = 0;


    public static boolean prepare(String path) {
        if (inputStream != null || fileChannel != null) {
            destroy();
        }
        try {
            inputStream = new FileInputStream(path);
            fileChannel = inputStream.getChannel();
            readFile(0, fileChannel.size(), 1, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void readBox(SpannableStringBuilder[] builders, Box box) {
        try {
            String className = MP4.getValue(box.getName());
            if (className.equals("")) {
                builders[0].append("暂不支持");
                builders[1].append("暂不支持");
                return;
            }
            Class<?> clz = Class.forName(className);
            Constructor constructor = clz.getConstructor(int.class);
            Method method = clz.getMethod("read", SpannableStringBuilder[].class, FileChannel.class, Box.class);
            method.invoke(constructor.newInstance(box.getLength()), builders, fileChannel, box);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<Box> getBox(int level, int parentId) {
        ArrayList<Box> list = new ArrayList<>();
        for (Box b : boxList) {
            if (b.getLevel() == level && b.getParentId() == parentId) {
                list.add(b);
            }
        }
        return list;
    }

    private static void readFile(final int pos, long max, int level, int parentId) {
        Log.e(TAG, "readFile: pos" + pos + "\nmax" + max + "\nlevel" + level + "\nparentId" + parentId);
        byte[] lengthArr = new byte[4];
        byte[] typeArr = new byte[4];
        try {
            int position = pos;
            synchronized (fileChannel) {
                do {
                    Log.e(TAG, "readFile: id" + id + "\npos" + position + "\nmax" + max);
                    fileChannel.position(position);
                    fileChannel.read(buffer);
                    buffer.flip();
                    Log.e(TAG, "readFile: buffer容量" + buffer.limit() + "\n获取的数组大小" + lengthArr.length);
                    buffer.get(lengthArr);
                    buffer.get(typeArr);
                    int length = CharUtil.c2Int(lengthArr);
                    String type = new String(typeArr);
                    buffer.clear();
                    boxList.add(new Box(type, id, position, length, level, parentId));
                    Log.e(TAG, "readFile: 执行添加程序");
                    position += length;
                    id++;
                    // TODO: 2019/11/18 需要处理==1时的largesize
                    if (length == 0 || length == 1) return;
                } while (position < max);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void destroy() {
        try {
            if (fileChannel != null) {
                fileChannel.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Box> readBox(SpannableStringBuilder[] builders, Box box, boolean isRead) {
        readBox(builders, box);
        Log.e(TAG, "readBox: box读取完毕");
        if (!isRead) {
            readFile(box.getPos() + box.getOffset() + 8, box.getPos() + box.getLength(), box.getLevel() + 1, box.getId());
            return getBox(box.getLevel() + 1, box.getId());
        }
        return null;
    }


    public static void readBox(SpannableStringBuilder builder, int pos, int length, FileChannel fileChannel, String[] name, String[] value, byte[][] data, String[] type) {
        new Thread(() -> {
            try {
                synchronized (fileChannel) {
                    MyHandler.clear();
                    Log.e(TAG, "readBox: 执行readbox" + length);
                    fileChannel.position(pos);
                    ByteBuffer boxBuffer = ByteBuffer.allocate(length).order(ByteOrder.nativeOrder());
                    fileChannel.read(boxBuffer);
                    Log.e(TAG, "readBox: " + Arrays.toString(name));
                    boxBuffer.flip();
                    Log.e(TAG, "readFile: 索引=" + pos + "\n可用=" + length + "\n总可用=" + (fileChannel.size() - pos) + "\n需要=" + data[0].length + "\n当前=" + boxBuffer.limit());
                    data[0] = new byte[length];
                    boxBuffer.get(data[0], 0, length);
                    value[0] = CharUtil.c2Str(data[0]);
                    if (name.length == 1) {
                        MyHandler.pushMessage(MyHandler.DATA_FINISH, new DataModel(name[0], CharUtil.changePrimevalData(data[0]), value[0]));
                    } else {
                        MyHandler.pushMessage(MyHandler.DATA_CONTINUE, new DataModel(name[0], CharUtil.changePrimevalData(data[0]), value[0]));
                    }
                    boxBuffer.position(0);
                    int last = 0;
                    for (int i = 1; i < name.length; i++) {
                        if (MyHandler.stop) {
                            break;
                        }
                        if (data[i].length == 0) {
                            value[i] = "null";
                            continue;
                        }
                        if ((boxBuffer.limit() - boxBuffer.position()) < data[i].length) {
                            boxBuffer.clear();
                            fileChannel.read(boxBuffer);
                            boxBuffer.flip();
                            boxBuffer.get(data[i]);
                            Log.e(TAG, "readBox: 从流中读取数据");
                        }
                        boxBuffer.get(data[i]);
                        switch (type[i]) {
                            case "char":
                                value[i] = CharUtil.c2Str(data[i]);
                                break;
                            case "int":
                                value[i] = CharUtil.c2Int(data[i]) + "";
                                last = CharUtil.c2Int(data[i]);
                                break;
                            case "time":
                                value[i] = CharUtil.c2Time(data[i]);
                                break;
                            case "duration":
                                value[i] = CharUtil.c2Duration(data[i]);
                                break;
                            case "matrix":
                                value[i] = CharUtil.c2Str(data[i]);
                                break;
                            case "fixed":
                                value[i] = (float) CharUtil.c2Fixed(data[i]) + "";
                                break;
                            case "timeScale":
                                value[i] = (float) CharUtil.c2TimeScale(data[i]) + "";
                                break;
                            case "null":
                                value[i] = "null";
                                break;
                            case "next is mult(8,last)":
                                last = CharUtil.c2Int(data[i]);
                                value[i] = last + "";
                                data[i + 1] = new byte[last];
                                break;
                            default:
                                value[i] = CharUtil.c2Str(data[i]);
                                break;
                        }
                        if (i == name.length - 1) {
                            MyHandler.pushMessage(MyHandler.DATA_FINISH, new DataModel(name[i], CharUtil.changePrimevalData(data[i]), value[i]));
                            Log.e(TAG, "readBox: 结束填充");
                        } else {
                            MyHandler.pushMessage(MyHandler.DATA_CONTINUE, new DataModel(name[i], CharUtil.changePrimevalData(data[i]), value[i]));
                            Log.e(TAG, "readBox: 继续填充");
                        }
                    }
                    boxBuffer.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void clear() {
        id = 0;
        MyHandler.stop = true;
        boxList.clear();
        MP4.TIME_SCALE = 0;
        destroy();
    }

    public static Box searchBox(int id) {
        return boxList.stream().filter(b -> id == b.getId()).findAny().orElse(null);
    }

    public static Box searchBox(String type) {
        return boxList.stream().filter(b -> type.equalsIgnoreCase(b.getName())).findAny().orElse(null);
    }

    public static void readItem(byte[] data, int pos, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(data);
        try {
            fileChannel.position(pos);
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(data);
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
