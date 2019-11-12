package com.yetote.mp4info;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class NIOReadInfo {
    private String path;
    private Map<String, Integer> indexMap;
    private static final String TAG = "Read";
    FileInputStream inputStream;
    FileChannel fileChannel;

    public NIOReadInfo(String path) {
        this.path = path;
        indexMap = new HashMap<>();

    }

    public void prepare() {
        ByteBuffer buffer = ByteBuffer.allocate(8).order(ByteOrder.nativeOrder());
        try {
            inputStream = new FileInputStream(path);
            fileChannel = inputStream.getChannel();
            int position = 0;
            int length = 0;
            String type = "";
            byte[] lengthArr = new byte[4];
            byte[] typeArr = new byte[4];
            for (; ; ) {
                fileChannel.position(position);
                fileChannel.read(buffer);
                buffer.flip();
                buffer.get(lengthArr);
                buffer.get(typeArr);
                length = toInt(lengthArr);
                type = new String(typeArr);
                Log.e(TAG, "prepare: type为:" + type + "长度:" + length);
                buffer.clear();
                position += length;
                if (position >= fileChannel.size()) break;
            }

            fileChannel.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private int toInt(byte[] arr) {
        int size = 0;
        for (int j = 0; j < arr.length; j++) {
            size += (arr[j] & 0xff) * Math.pow(16, 6 - 2 * j);
        }
        return size;
    }

}
