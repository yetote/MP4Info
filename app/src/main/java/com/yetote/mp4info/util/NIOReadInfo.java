package com.yetote.mp4info.util;

import android.util.Log;

import com.yetote.mp4info.model.Box;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NIOReadInfo {
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

    }

    public boolean prepare() {
        ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.nativeOrder());

        try {
            inputStream = new FileInputStream(path);
            fileChannel = inputStream.getChannel();
            int position = 0;
            int length = 0;
            String type = "";
            byte[] lengthArr = new byte[4];
            byte[] typeArr = new byte[4];
            do {
                fileChannel.position(position);
                fileChannel.read(buffer);
                buffer.flip();
                buffer.get(lengthArr);
                buffer.get(typeArr);
                length = toInt(lengthArr);
                type = new String(typeArr);
                buffer.clear();
//                indexMap.put(type, new Box(id, position, length, 1, 0));
                boxList.add(new Box(type, id, position, length, 1, 0));
                position += length;
                id++;
            } while (position < fileChannel.size());

            fileChannel.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void readChild() {

    }


    private int toInt(byte[] arr) {
        int size = 0;
        for (int j = 0; j < arr.length; j++) {
            size += (arr[j] & 0xff) * Math.pow(16, 6 - 2 * j);
        }
        return size;
    }

    public List<Box> getBox(int level, int parentId) {

        if (boxList.size() > 0) return boxList;
        return null;
    }
}
