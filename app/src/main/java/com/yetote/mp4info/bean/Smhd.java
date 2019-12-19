package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Smhd extends FullBox {
    String describe = "Sound media header, 该box指定了音频信息头";
    private String[] key = new String[]{
            "balance",
            "reserved",
    };
    private String[] introductions = new String[]{
            "均衡,是一个8.8的定点数，全部左声道为-1.0，全部右声道为1.0",
            "保留位",
    };
    private int balance_size = 2;
    private int reserved_size = 2;

    private byte[] all;
    private byte[] balance_arr;
    private byte[] reserved_arr;

    public Smhd(int length) {
        balance_arr = new byte[balance_size];
        reserved_arr = new byte[reserved_size];
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        String[] name = new String[]{"全部数据", "length", "type", "version", "flag",
                "balance",
                "reserved"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version_arr, flag_arr,
                balance_arr,
                reserved_arr
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char", "int", "int",
                "fixed",
                "char"
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
