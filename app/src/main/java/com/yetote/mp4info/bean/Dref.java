package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.lang.reflect.Field;
import java.nio.channels.FileChannel;

public class Dref extends FullBox {
    String describe = "描述媒体信息在track的位置";
    private String[] key = new String[]{
            "version",
            "flag",
            "entry_count",
            "entry_box_length",
            "entry_box_type",
            "entry_version",
            "entry_flags",
            "data_entry",
    };
    private String[] introductions = new String[]{
            "版本号",
            "标志码",
            "子box的数量(url、urn)",
            "子box的长度",
            "entry_box_type：子box的类型\n" +
                    "\nPS:entry_box_length和entry_box_type在官方文档中并不存在，它们应该代表Dref子box，由于子box代码很少，所以这里合在一起解析了\n",
            "子box的版本号",
            "子box的标志码",
            "子box数据(可为空)",
    };
    private int version_size = 1;
    private int flag_size = 3;
    private int entry_count_size = 4;
    private int entry_box_length_size = 4;
    private int entry_box_type_size = 4;
    private int entry_version_size = 1;
    private int entry_flags_size = 3;
    private int data_entry_size = 0;

    private byte[] version;
    private byte[] flag;
    private byte[] entry_count;
    private byte[] entry_box_length;
    private byte[] entry_box_type;
    private byte[] entry_version;
    private byte[] entry_flags;
    private byte[] data_entry;
    private byte[] all;


    public Dref(int length) {
        data_entry_size = length - 28;
        version = new byte[version_size];
        flag = new byte[flag_size];
        entry_count = new byte[entry_count_size];
        entry_box_length = new byte[entry_box_length_size];
        entry_box_type = new byte[entry_box_type_size];
        entry_version = new byte[entry_version_size];
        entry_flags = new byte[entry_flags_size];
        data_entry = new byte[data_entry_size];
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        String[] name;
        String[] value;
        String[] type;
        byte[][] data;
        if (data_entry_size != 0) {
            name = new String[]{"全部数据", "length", "type", "version", "flag",
                    "entry_count",
                    "entry_box_length",
                    "entry_box_type",
                    "entry_version",
                    "entry_flags",
                    "data_entry"};
            data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version, flag,
                    entry_count,
                    entry_box_length,
                    entry_box_type,
                    entry_version,
                    entry_flags,
                    data_entry,
            };
            value = new String[name.length];
            type = new String[]{"char", "int", "char", "int", "int",
                    "int",
                    "int",
                    "char",
                    "int",
                    "int",
                    "char",
            };
        } else {
            name = new String[]{"全部数据", "length", "type", "version", "flag",
                    "entry_count",
                    "entry_box_length",
                    "entry_box_type",
                    "entry_version",
                    "entry_flags",
            };
            data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version, flag,
                    entry_count,
                    entry_box_length,
                    entry_box_type,
                    entry_version,
                    entry_flags,
            };
            value = new String[name.length];
            type = new String[]{"char", "int", "char", "int", "int",
                    "int",
                    "int",
                    "char",
                    "int",
                    "int",
            };
        }
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
