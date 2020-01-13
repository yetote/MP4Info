package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public abstract class BasicBox {
    protected int length_size = 4;
    protected int large_length_size = 8;
    protected int type_size = 4;
    protected byte[] length_arr;
    protected byte[] large_length_arr;
    protected byte[] type_arr;
    protected String type;
    protected int length;
    protected long large_length;
    private static final String TAG = "BasicBox";
    private byte[] all;

    public BasicBox() {
        length_arr = new byte[length_size];
        large_length_arr = new byte[large_length_size];
        type_arr = new byte[type_size];
    }


    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        ByteBuffer buffer = ByteBuffer.allocate(length_size + type_size).order(ByteOrder.nativeOrder());
        try {
            fileChannel.position(box.getPos());
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(length_arr);
            length = CharUtil.c2Int(length_arr);
            buffer.get(type_arr);
            type = CharUtil.c2Str(type_arr);
            if (length == 1) {
                fileChannel.read(buffer);
                buffer.flip();
                buffer.get(large_length_arr);
                large_length = CharUtil.c2long(large_length_arr);
                Log.e(TAG, "read:largeLength " + large_length);
            } else if (length == 0) {
                length = (int) (fileChannel.size() - box.getPos());
                Log.e(TAG, "read:length " + length);
            }
            Log.e(TAG, "read: BasicLength" + length);
            length = Math.min(1000, length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
