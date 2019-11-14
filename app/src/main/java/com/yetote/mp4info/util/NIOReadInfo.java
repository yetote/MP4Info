package com.yetote.mp4info.util;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NIOReadInfo {
    private ByteBuffer buffer;
    private String path;
    private Map<String, Box> indexMap;
    private List<Box> boxList = new ArrayList<>();
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
            if (className == null) return;
            Class<?> clz = Class.forName(className);
            Constructor constructor = clz.getConstructor(int.class);
            Method method = clz.getMethod("read", SpannableStringBuilder[].class, int.class, int.class, FileChannel.class);
            method.invoke(constructor.newInstance(box.getLength()), builders, box.getPos(), box.getLength(), fileChannel);
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
                int length = CharUtil.c2Int(lengthArr);
                String type = new String(typeArr);
                buffer.clear();
                boxList.add(new Box(type, id, pos, length, level, parentId));
                pos += length;
                Log.e(TAG, "readFile:" + boxList.get(id).toString());
                id++;
            } while (pos < max);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void destroy() {
        try {
            fileChannel.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Box> readBox(SpannableStringBuilder[] builders, Box box, boolean isRead) {
        try {
            if (box.getLevel() != 0) {
                String className = MP4.getValue(box.getName());
                Class clz = Class.forName(className);
                Field field = clz.getDeclaredField("describe");
                field.setAccessible(true);
                String describe = (String) field.get(clz.newInstance());
                if (describe != null) {
                    builders[0] = new SpannableStringBuilder();
                    builders[0].append(describe);
                    Log.e(TAG, "readBox: describe" + describe);
                }
            }
            if (!isRead) {
                readFile(box.getPos() + 8, box.getPos() + box.getLength(), box.getLevel() + 1, box.getId());
                return getBox(box.getLevel() + 1, box.getId());
            }
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void readBox(SpannableStringBuilder builder, int pos, int length, FileChannel fileChannel, String[] name, String[] value, byte[][] data) {
        try {
            fileChannel.position(pos);
            ByteBuffer boxBuffer = ByteBuffer.allocate(length).order(ByteOrder.nativeOrder());
            fileChannel.read(boxBuffer);

            boxBuffer.flip();
            byte[] all = new byte[length];
            boxBuffer.get(all);
            value[0] = CharUtil.c2Str(all);

            boxBuffer.position(8);
            for (int i = 1; i < value.length; i++) {
                boxBuffer.get(data[i]);
                value[i] = CharUtil.c2Str(data[i]);
            }
            boxBuffer.clear();
            CharUtil.linkDataString(builder, name, data, value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
