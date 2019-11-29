package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.NIOReadInfo;

import java.lang.reflect.Field;
import java.nio.channels.FileChannel;

public class Dref extends FullBox {
    String describe = "描述媒体信息在track的位置\n" +
            "version:版本号\n" +
            "flag：标志码\n" +
            "entry_count:子box的数量(url、urn)\n" +
            "entry_box_length:子box的长度\n" +
            "entry_box_type：子box的类型\n" +
            "       ps:entry_box_length和entry_box_type在官方文档中并不存在，它们应该代表Dref子box，由于子box代码很少，所以这里合在一起解析了\n" +
            "entry_version：子box的版本号\n" +
            "entry_flags：子box的标志码\n" +
            "data_entry：子box数据(可为空)\n";

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
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "version", "flag",
                "entry_count",
                "entry_box_length",
                "entry_box_type",
                "entry_version",
                "entry_flags",
                "data_entry"};
        byte[][] data = new byte[][]{all, version, flag,
                entry_count,
                entry_box_length,
                entry_box_type,
                entry_version,
                entry_flags,
                data_entry,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "int",
                "int",
                "int",
                "char",
                "int",
                "int",
                "null",
        };
        if (data_entry_size != 0) {
            type[type.length - 1] = "char";
        }
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
