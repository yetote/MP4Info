package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;

import java.nio.channels.FileChannel;

public class Moov extends BasicBox {
    String describe = "moov定义了媒体文件的中的数据信息,至少包括以下三种容器之一\n" +
            "mvhd:Movie Header Box,存放为压缩过的影片信息容器\n" +
            "cmov:Compressed Movie Box,压缩过的影片信息容器(不常用)\n" +
            "rmra:Reference Moview Box,参考电影信息容器(不常用)\n";

    public Moov(int length) {
    }
    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
