package com.yetote.mp4info.model;

public class Trak {
    String describe = "trak容器定义了媒体中的某一个track信息，一个媒体文件中可以包括多个trak容器，各容器间相互独立。\n" +
            "trak有两个目的：\n" +
            "    1、包含媒体轨道信息\n" +
            "    2、包含流媒体数据打包协议(hint track)\n" +
            "一个trak至少要包括一个tkhd容器和一个mdia容器";

    public Trak() {
    }
}
