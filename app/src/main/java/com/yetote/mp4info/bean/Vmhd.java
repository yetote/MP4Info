package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Vmhd extends FullBox {
    String describe = "视频信息头，此box包含了视频的特征信息";

    private String[] key = new String[]{
            "version",
            "flag",
            "graphicsmode",
            "opcolor",

    };
    private String[] introductions = new String[]{
            "版本号",
            "标志码",
            "视频合成模式;0为复制现有模式",
            "rgb值，供graphics_mode使用",
    };
    private int graphicsmode_size = 2;
    private int opcolor_size = 6;

    private byte[] graphicsmode;
    private byte[] opcolor;
    private byte[] all;

    public Vmhd(int length) {
        graphicsmode = new byte[graphicsmode_size];
        opcolor = new byte[opcolor_size];
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        String[] name = new String[]{"全部数据", "length", "type", "version", "flag",
                "graphicsmode",
                "opcolor"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version_arr, flag_arr,
                graphicsmode,
                opcolor
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char", "int", "int",
                "char",
                "char"
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
