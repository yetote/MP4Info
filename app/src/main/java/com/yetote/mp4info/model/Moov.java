package com.yetote.mp4info.model;

public class Moov {
    String describe = "moov定义了媒体文件的中的数据信息,至少包括以下三种容器之一\n" +
            "mvhd:Movie Header Atom,存放为压缩过的影片信息容器\n" +
            "cmov:Compressed Movie Atom,压缩过的影片信息容器(不常用)\n" +
            "rmra:Reference Moview Atom,参考电影信息容器(不常用)\n";

    public Moov() {
    }
}