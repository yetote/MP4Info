package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Tkhd extends FullBox {
    String describe = "该box用于描述对应的trak信息";
    private String[] key = new String[]{
            "version",
            "flag",
            "creation_time",
            "modification_time",
            "track_ID",
            "reserved",
            "duration",
            "reserved",
            "layer",
            "alternate_group",
            "volume",
            "reserved",
            "matrix",
            "width",
            "height",
    };
    private String[] introductions = new String[]{
            "版本号",
            "标志码:\n" +
                    "       1、0x000001(track_enable)：指示该track启用\n" +
                    "       2、0x000002(track_in_movie)：指示该track用于播放\n" +
                    "       3、0x000004(track_in_preview)：指示该track用于预览\n" +
                    "       4、0x000008(track_size_is_aspect_ratio)：指示该track的宽高未以像素为单位表示，而是用的宽高比\n",
            "媒体文件创建时间（从1904-01-01 00:00:00开始计算，单位：秒）",
            "媒体文件最新修改时间（从1904-01-01 00:00:00开始计算，单位：秒。该数值并不一定准确）",
            "指示此track的id，唯一且不为0",
            "保留字段",
            "时长（需要与mvhd的timescale进行计算）",
            "保留字段",
            "视频层的顺序，较小的在上层",
            "track分组信息",
            "音量",
            "保留字段",
            "矩阵",
            "宽",
            "高" +
                    "\nPS:width和height描述字幕等文本信息时，可以使用flag=0x000008（track_size_is_aspect_ratio）进行描述；对于非视觉轨道(如音轨),则应将其设置为0",
    };
    private int creation_time_size = 4;
    private int modification_time_size = 4;
    private int track_ID_size = 4;
    private int reserved_size = 4;
    private int duration_size = 4;
    private int reserved_one_size = 8;
    private int layer_size = 2;
    private int alternate_group_size = 2;
    private int volume_size = 2;
    private int reserved_two_size = 2;
    private int matrix_size = 36;
    private int width_size = 4;
    private int height_size = 4;

    private byte[] creation_time;
    private byte[] modification_time;
    private byte[] track_ID;
    private byte[] reserved;
    private byte[] duration;
    private byte[] reserved_one;
    private byte[] layer;
    private byte[] alternate_group;
    private byte[] volume;
    private byte[] reserved_two;
    private byte[] matrix;
    private byte[] width;
    private byte[] height;
    private byte[] all;

    public Tkhd(int length) {
        creation_time = new byte[creation_time_size];
        modification_time = new byte[modification_time_size];
        track_ID = new byte[track_ID_size];
        reserved = new byte[reserved_size];
        duration = new byte[duration_size];
        reserved_one = new byte[reserved_one_size];
        layer = new byte[layer_size];
        alternate_group = new byte[alternate_group_size];
        volume = new byte[volume_size];
        reserved_two = new byte[reserved_two_size];
        matrix = new byte[matrix_size];
        width = new byte[width_size];
        height = new byte[height_size];
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        String[] name = new String[]{"全部数据", "length", "type", "version", "flag",
                "creation_time",
                "modification_time",
                "track_ID",
                "reserved",
                "duration",
                "reserved_one",
                "layer",
                "alternate_group",
                "volume",
                "reserved_two",
                "matrix",
                "width",
                "height"
        };
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version_arr, flag_arr,
                creation_time,
                modification_time,
                track_ID,
                reserved,
                duration,
                reserved_one,
                layer,
                alternate_group,
                volume,
                reserved_two,
                matrix,
                width,
                height,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char", "int", "int",
                "time",
                "time",
                "int",
                "char",
                "time",
                "char",
                "int",
                "int",
                "fixed",
                "char",
                "matrix",
                "fixed",
                "fixed"};
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
