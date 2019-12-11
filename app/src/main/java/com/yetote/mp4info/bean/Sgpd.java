package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Sgpd extends FullBox {

    String describe = "SampleGroupDescription Box, 该box用于描述sample group信息\n" +
            "version：版本号\n" +
            "flag：标志码\n" +
            "balance：均衡,是一个8.8的定点数，全部左声道为-1.0，全部右声道为1.0\n" +
            "reserved：保留位\n";

    private int balance_size = 2;
    private int reserved_size = 2;

    private byte[] all;
    private byte[] balance_arr;
    private byte[] reserved_arr;

    public Sgpd(int length) {
//        balance_arr = new byte[balance_size];
//        reserved_arr = new byte[reserved_size];
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据"
        };
        byte[][] data = new byte[][]{all

        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char"
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
