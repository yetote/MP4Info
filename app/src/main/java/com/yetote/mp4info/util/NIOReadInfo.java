package com.yetote.mp4info.util;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.DataModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NIOReadInfo {
    private ByteBuffer buffer;
    private String path;
    private Map<String, Box> indexMap;
    private static List<Box> boxList = new ArrayList<>();
    private static final String TAG = "Read";
    FileInputStream inputStream;
    FileChannel fileChannel;
    private static int id = 0;

    public NIOReadInfo(String path) {
        this.path = path;
        indexMap = new HashMap<>();
        buffer = ByteBuffer.allocate(8).order(ByteOrder.nativeOrder());
    }

    public boolean prepare() {
        try {
            inputStream = new FileInputStream(path);
            fileChannel = inputStream.getChannel();

            readFile(0, fileChannel.size(), 1, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void readBox(SpannableStringBuilder[] builders, Box box) {
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


    public ArrayList<Box> getBox(int level, int parentId) {
        ArrayList<Box> list = new ArrayList<>();
        for (Box b : boxList) {
            if (b.getLevel() == level && b.getParentId() == parentId) {
                list.add(b);
            }
        }
        return list;
    }

    private void readFile(int pos, long max, int level, int parentId) {
        byte[] lengthArr = new byte[4];
        byte[] typeArr = new byte[4];
        try {
            do {
                fileChannel.position(pos);
                fileChannel.read(buffer);
                buffer.flip();
                buffer.get(lengthArr);
                buffer.get(typeArr);
                Log.e(TAG, "readFile: length " + Arrays.toString(lengthArr));
                int length = CharUtil.c2Int(lengthArr);
                Log.e(TAG, "readFile: type" + Arrays.toString(typeArr));
                String type = new String(typeArr);
                buffer.clear();
                boxList.add(new Box(type, id, pos, length, level, parentId));
                pos += length;
                Log.e(TAG, "readFile:" + boxList.get(id).toString());
                id++;
                // TODO: 2019/11/18 需要处理==1时的largesize
                if (length == 0 || length == 1) return;
            } while (pos < max);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void destroy() {
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

    public ArrayList<Box> readBox(SpannableStringBuilder[] builders, Box box, boolean isRead) {
        Log.e(TAG, "readBox: ");
//        try {
//            if (box.getLevel() != 0) {
//                String className = MP4.getValue(box.getName());
//                Class clz = Class.forName(className);
//                Field field = clz.getDeclaredField("describe");
//                field.setAccessible(true);
//                String describe = (String) field.get(clz.newInstance());
//                if (describe != null) {
//                    builders[0] = new SpannableStringBuilder();
//                    builders[0].append(describe);
//                    Log.e(TAG, "readBox: describe" + describe);
//                }
//            }
        readBox(builders, box);
        if (!isRead) {
            Log.e(TAG, "readBox: offset" + box.getOffset());
            readFile(box.getPos() + box.getOffset() + 8, box.getPos() + box.getLength(), box.getLevel() + 1, box.getId());
            Log.e(TAG, "readBox: " + builders[0].toString());
            return getBox(box.getLevel() + 1, box.getId());
        }


        return null;
    }


    public static void readBox(SpannableStringBuilder builder, int pos, int length, FileChannel fileChannel, String[] name, String[] value, byte[][] data, String[] type) {
        new Thread(() -> {
            try {
                MyHandler.clear();
                fileChannel.position(pos);
                ByteBuffer boxBuffer = ByteBuffer.allocate(length).order(ByteOrder.nativeOrder());
                fileChannel.read(boxBuffer);

                boxBuffer.flip();
                boxBuffer.get(data[0]);
                value[0] = CharUtil.c2Str(data[0]);
                MyHandler.pushMessage(MyHandler.DATA_CONTINUE, new DataModel(name[0], CharUtil.changePrimevalData(data[0]), value[0]));
                int last = 0;
                boxBuffer.position(0);
                for (int i = 1; i < name.length; i++) {
                    if (MyHandler.stop) {
                        break;
                    }
                    if (data[i].length == 0) {
                        value[i] = "null";
                        continue;
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
                            value[i] = CharUtil.c2Str(data[i]);
                            break;
                        case "matrix":
                            value[i] = CharUtil.c2Str(data[i]);
                            break;
                        case "fixed":
                            value[i] = (float) CharUtil.c2Fixed(data[i]) + "";
                            last = CharUtil.c2Int(data[i]);
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
                    } else {
                        MyHandler.pushMessage(MyHandler.DATA_CONTINUE, new DataModel(name[i], CharUtil.changePrimevalData(data[i]), value[i]));
                    }
                }
                boxBuffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void clear() {
        id = 0;
        MyHandler.stop = true;
        boxList.clear();
        destroy();
    }

    public static String searchBox(int id) {
        Box box = boxList.stream().filter(b -> id == b.getId()).findAny().orElse(null);
        return box.getName();
    }
}
