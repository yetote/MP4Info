package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Esds extends FullBox {
    String describe = "暂不支持";

    private byte[] all;

    public Esds(int length) {
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type", "version", "flag"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version_arr, flag_arr};
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char", "int", "int"};

        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
