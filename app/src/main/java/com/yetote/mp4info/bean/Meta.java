package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Meta extends FullBox {
    String describe = "一个通用的基本结构，用于包含通用的MetaData";

    private byte[] all;

    public Meta(int length) {
        if (length>=1000) length=1000;
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        if (NIOReadInfo.searchBox(box.getParentId()).getName().equalsIgnoreCase("udta")) {
            box.setOffset(version_size + flag_size);
        }
        String[] name = new String[]{"全部数据", "length", "type", "version", "flag"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version_arr, flag_arr};
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char", "int", "int"};

        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
