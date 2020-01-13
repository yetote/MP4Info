package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Moov extends BasicBox {
    String describe = "moov定义了媒体文件的中的数据信息,至少包括以下三种容器之一\n" +
            "mvhd:Movie Header Box,存放为压缩过的影片信息容器\n" +
            "cmov:Compressed Movie Box,压缩过的影片信息容器(不常用)\n" +
            "rmra:Reference Moview Box,参考电影信息容器(不常用)\n" +
            "\nPS:省略部分数据\n";
    private byte[] all;

    public Moov(int length) {
        if (length > 1000) length = 1000;
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type",};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr};
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char",};

        NIOReadInfo.readBox(builders[1], box.getPos(),  Math.min(length, 1000), fileChannel, name, value, data, type);
    }
}
