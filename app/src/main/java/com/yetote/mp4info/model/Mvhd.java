package com.yetote.mp4info.model;

public class Mvhd {
    String describe = "moov定义了媒体文件的中的数据信息,至少包括以下三种容器之一\n" +
            "mvhd:Movie Header Atom,存放为压缩过的影片信息容器\n" +
            "cmov:Compressed Movie Atom,压缩过的影片信息容器(不常用)\n" +
            "rmra:Reference Moview Atom,参考电影信息容器(不常用)\n";


    private int version_size = 1;
    private int flag_size = 1;
    private int creation_time_size = 1;
    private int modification_time_size = 1;
    private int time_scale_size = 1;
    private int duration_size = 1;
    private int rate_size = 1;
    private int volume_size = 1;
    private int reserved_size = 1;
    private int matrix_size = 1;
    private int pre_defined_size = 1;
    private int next_track_id_size = 1;

}
