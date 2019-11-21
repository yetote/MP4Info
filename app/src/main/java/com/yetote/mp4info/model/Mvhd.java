package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Mvhd {
    String describe = "该box在文件中唯一，对整个媒体文件进行了全局的描述（这些信息与媒体数据无关，只是对媒体文件的描述）\n" +
            "version:version为0时，creation_time、modification_time、timescale、duration长度为32bit；为1时，长度为64bit\n" +
            "flag:标志位\n" +
            "creation_time:媒体文件创建时间（从1904-01-01 00:00:00开始计算，单位：秒）\n" +
            "modification_time：媒体文件最新修改时间（从1904-01-01 00:00:00开始计算，单位：秒。该数值并不一定准确）\n" +
            "time_scale：该数值表示整个文件的单位，表示将1s划分为多少份。例如：time_scale=1000，则本文件时间单位为1/1000s=1ms\n" +
            "duration：媒体可播放最长时间，需要与time_scale计算才能得到实际时间。（所有轨道中的最长持续时间）\n" +
            "rate:文件播放速率，0x00010000代表播放速率为1.0（正常速率）\n" +
            "volume：音量，0x0100表示音量为1.0（最大音量）\n" +
            "reserved：保留数据\n" +
            "matrix：提供视频的转换矩阵(貌似用于视频画面缩放)；（u，v，w）在这里限制为（0,0,1），十六进制值（0,0,0x40000000）。\n" +
            "pre_defined：不清楚，文档没解释\n" +
            "next_track_id：下一个待添加的轨道id，0不是有效值。如果该值全为1并且需要添加新轨道，则必须搜索未使用的轨道\n";

    private int version_size = 1;
    private int flag_size = 3;
    private int creation_time_size = 4;
    private int modification_time_size = 4;
    private int time_scale_size = 4;
    private int duration_size = 4;
    private int rate_size = 4;
    private int volume_size = 2;
    private int reserved_size = 10;
    private int matrix_size = 36;
    private int pre_defined_size = 24;
    private int next_track_id_size = 4;

    private byte[] version;
    private byte[] flag;
    private byte[] creation_time;
    private byte[] modification_time;
    private byte[] time_scale;
    private byte[] duration;
    private byte[] rate;
    private byte[] volume;
    private byte[] reserved;
    private byte[] matrix;
    private byte[] pre_defined;
    private byte[] next_track_id;
    private byte[] all;

    public Mvhd(int length) {
        version = new byte[version_size];
        flag = new byte[flag_size];
        creation_time = new byte[creation_time_size];
        modification_time = new byte[modification_time_size];
        time_scale = new byte[time_scale_size];
        duration = new byte[duration_size];
        rate = new byte[rate_size];
        volume = new byte[volume_size];
        reserved = new byte[reserved_size];
        matrix = new byte[matrix_size];
        pre_defined = new byte[pre_defined_size];
        next_track_id = new byte[next_track_id_size];
        all = new byte[length];
    }

    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel) {
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "version", "flag",
                "creation_time",
                "modification_time",
                "time_scale",
                "duration",
                "rate",
                "volume",
                "reserved",
                "matrix",
                "pre_defined",
                "next_track_id"};
        byte[][] data = new byte[][]{all, version, flag,
                creation_time,
                modification_time,
                time_scale,
                duration,
                rate,
                volume,
                reserved,
                matrix,
                pre_defined,
                next_track_id};
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "int",
                "time",
                "time",
                "int",
                "time",
                "fixed",
                "fixed",
                "char",
                "matrix",
                "char",
                "int"};
        NIOReadInfo.readBox(builders[1], pos, length, fileChannel, name, value, data, type);
    }
}
