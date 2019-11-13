package com.yetote.mp4info.util;

import android.util.Log;

import com.yetote.mp4info.model.Box;

import java.io.FileInputStream;
import java.io.IOException;
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

    public String[] readBox(Box box) {
        try {
            String className = MP4.getValue(box.getName());
            if (className == null) return null;
            Class<?> clz = Class.forName(className);
            Method method = clz.getMethod("read", int.class, int.class, FileChannel.class);
            return (String[]) method.invoke(clz.newInstance(), box.getPos(), box.getLength(), fileChannel);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
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

    public ArrayList<Box> readBox(Box box, boolean isChild) {
        readFile(box.getPos() + 8, box.getPos() + box.getLength(), box.getLevel() + 1, box.getId());
        return getBox(box.getLevel() + 1, box.getId());
    }
}
