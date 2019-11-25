package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Stsd {
    String describe = "Sample Describe 该box描述了编码类型与编码初始化所需需要的参数\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n"+
            "\n";

    private int version_size=1;
    private int flag_size=3;
    private int entry_count_size=4;
    private int reserved_size=6;
    private int data_reference_index_size=2;
//    private int version_size=1;
//    private int version_size=1;
//    private int version_size=1;
//    private int version_size=1;
//    private int version_size=1;
//    private int version_size=1;

    private byte[] all;

    public Stsd(int length) {
        all = new byte[length];
    }

    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel) {
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据"
        };
        byte[][] data = new byte[][]{all
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char"};
        NIOReadInfo.readBox(builders[1], pos, length, fileChannel, name, value, data, type);
    }
}
