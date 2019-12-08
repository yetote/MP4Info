package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Vmhd extends FullBox {
    String describe = "视频信息头，此box包含了视频的特征信息\n" +
            "version:版本号\n" +
            "flag:标志码\n" +
            "graphicsmode:视频合成模式;0为复制现有模式\n" +
            "opcolor:rgb值，供graphics_mode使用\n";

    private int version_size = 1;
    private int flag_size = 3;
    private int graphicsmode_size = 2;
    private int opcolor_size = 6;

    private byte[] version;
    private byte[] flag;
    private byte[] graphicsmode;
    private byte[] opcolor;
    private byte[] all;

    public Vmhd(int length) {
        version = new byte[version_size];
        flag = new byte[flag_size];
        graphicsmode = new byte[graphicsmode_size];
        opcolor = new byte[opcolor_size];
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type", "version", "flag",
                "graphicsmode",
                "opcolor"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version, flag,
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
