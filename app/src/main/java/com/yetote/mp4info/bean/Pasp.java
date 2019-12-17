package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Pasp extends BasicBox {
    String describe = "Pixel Aspect Ratio Box,指明了像素长宽比，\n" +
            "hSpacing：像素的相对高度\n" +
            "vSpacing：像素的相对宽度\n";

    private int hSpacing_size = 4;
    private int vSpacing_size = 4;

    private byte[] all;
    private byte[] hSpacing_arr;
    private byte[] vSpacing_arr;

    public Pasp(int length) {
        all = new byte[length];
        hSpacing_arr = new byte[hSpacing_size];
        vSpacing_arr = new byte[vSpacing_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type",
                "hSpacing",
                "vSpacing"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                hSpacing_arr,
                vSpacing_arr};
        String[] value = new String[6];
        String[] type = new String[]{"char", "int", "char",
                "int",
                "int"};
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
