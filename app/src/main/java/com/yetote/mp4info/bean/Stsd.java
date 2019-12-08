package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.NIOReadInfo;

import java.lang.reflect.Field;
import java.nio.channels.FileChannel;

public class Stsd extends FullBox {
    String describe = "Sample Describe 该box描述了编码类型与编码初始化所需需要的参数\n" +
            "version：版本号\n" +
            "flag：标志码\n" +
            "entry_count：含有子box的数量\n";
//    +
//            "reserved:保留位\n" +
//            "data_reference_index：数据引用索引（没搞懂）\n";

    private int version_size = 1;
    private int flag_size = 3;
    private int entry_count_size = 4;
    //    private int reserved_size = 6;
//    private int data_reference_index_size = 2;
    private byte[] version;
    private byte[] flag;
    private byte[] entry_count;
    //    private byte[] reserved;
//    private byte[] data_reference_index;
    private byte[] all;

    public Stsd(int length) {
        all = new byte[length];
        version = new byte[version_size];
        flag = new byte[flag_size];
        entry_count = new byte[entry_count_size];
//        reserved = new byte[reserved_size];
//        data_reference_index = new byte[data_reference_index_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        box.setOffset(entry_count_size + version_size + flag_size);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type","version", "flag",
                "entry_count",
//                "reserved_size",
//                "data_reference_index_size"
        };
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,version, flag,
                entry_count,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char","int", "int",
                "int",
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
