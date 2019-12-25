package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Trak extends BasicBox {
    String describe = "trak容器定义了媒体中的某一个track信息，一个媒体文件中可以包括多个trak容器，各容器间相互独立。\n" +
            "trak有两个目的：\n" +
            "       1、包含媒体轨道信息\n" +
            "       2、包含流媒体数据打包协议(hint track)\n" +
            "一个trak至少要包括一个tkhd容器和一个mdia容器";
    private byte[] all;

    public Trak(int length) {
        if (length >= 1000) length = 1000;
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type",};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,};
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char",};

        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
