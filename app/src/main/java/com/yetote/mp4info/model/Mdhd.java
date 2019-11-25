package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Mdhd {
    String describe = "mdhd说明该track的信息\n" +
            "version:版本号\n" +
            "flag:标志码\n" +
            "creation_time:创建时间\n" +
            "modification_time:最新修改时间\n" +
            "time_scale:时间单位\n" +
            "duration:总时长\n" +
            "pad: 不明\n" +
            "language:使用的语言 \n" +
            "pre_define:不明\n" +
            "";


    private int version_size = 1;
    private int flag_size = 3;
    private int creation_time_size = 4;
    private int modification_time_size = 4;
    private int time_scale_size = 4;
    private int duration_size = 4;
    private int pad_size = 0;
    private int language_size = 2;
    private int pre_define_size = 2;

    private byte[] version;
    private byte[] flag;
    private byte[] creation_time;
    private byte[] modification_time;
    private byte[] time_scale;
    private byte[] duration;
    private byte[] pad;
    private byte[] language;
    private byte[] pre_define;
    private byte[] all;

    public Mdhd(int length) {
        version = new byte[version_size];
        flag = new byte[flag_size];
        creation_time = new byte[creation_time_size];
        modification_time = new byte[modification_time_size];
        time_scale = new byte[time_scale_size];
        duration = new byte[duration_size];
        language = new byte[language_size + pad_size];
        pre_define = new byte[pre_define_size];
        all = new byte[length];
    }

    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel,Box box) {
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "version", "flag",
                "creation_time",
                "modification_time",
                "time_scale",
                "duration",
                "language",
                "pre_define"};
        byte[][] data = new byte[][]{all, version, flag,
                creation_time,
                modification_time,
                time_scale,
                duration,
                language,
                pre_define,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "int",
                "time",
                "time",
                "int",
                "time",
                "int",
                "char",
        };
        NIOReadInfo.readBox(builders[1], pos, length, fileChannel, name, value, data, type);
    }
}