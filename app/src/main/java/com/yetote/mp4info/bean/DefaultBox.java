package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class DefaultBox extends BasicBox {
    private String describe = "暂不支持";
    private byte[] all;

    public DefaultBox(int length) {
        if (length>=1000) length=1000;
        all = new byte[length];
    }
    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr};
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char"};

        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
