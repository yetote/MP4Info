package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.util.CharUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public abstract class FullBox extends BasicBox {
    protected int version_size = 1;
    protected int flag_size = 3;
    protected byte[] version_arr;
    protected byte[] flag_arr;
    protected int version;
    protected int flag;

    public FullBox() {
        version_arr = new byte[version_size];
        flag_arr = new byte[flag_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
        try {
            fileChannel.position(box.getPos() + 8 + (length == 1 ? 8 : 0));
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(version_arr);
            buffer.get(flag_arr);
            version = CharUtil.c2Int(version_arr);
            flag = CharUtil.c2Int(flag_arr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
