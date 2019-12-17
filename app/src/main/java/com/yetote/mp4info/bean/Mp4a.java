package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Mp4a extends BasicBox {
    String describe = "Sample Description Boxes, 存储了该流的采样信息\n" +
            "reserved：保留\n" +
            "data_reference_index：数据引用索引\n" +
            "reserved：保留\n" +
            "channelcount：声道数\n" +
            "samplesize：量化精度\n" +
            "pre_defined：预定义\n" +
            "reserved：保留\n" +
            "samplerate：采样率\n";

    private int reserved_one_size = 6;
    private int data_reference_index_size = 2;
    private int reserved_two_size = 8;
    private int channelcount_size = 2;
    private int samplesize_size = 2;
    private int pre_defined_size = 2;
    private int reserved_three_size = 2;
    private int samplerate_size = 4;

    private byte[] all;
    private byte[] reserved_one_arr;
    private byte[] channelcount_arr;
    private byte[] data_reference_index_arr;
    private byte[] samplesize_arr;
    private byte[] pre_defined_arr;
    private byte[] reserved_two_arr;
    private byte[] reserved_three_arr;
    private byte[] samplerate_arr;

    public Mp4a(int length) {
        all = new byte[length];
        reserved_one_arr = new byte[reserved_one_size];
        data_reference_index_arr = new byte[data_reference_index_size];
        reserved_two_arr = new byte[reserved_two_size];
        channelcount_arr = new byte[channelcount_size];
        samplesize_arr = new byte[samplesize_size];
        pre_defined_arr = new byte[pre_defined_size];
        reserved_three_arr = new byte[reserved_three_size];
        samplerate_arr = new byte[samplerate_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        box.setOffset(reserved_one_size +
                data_reference_index_size +
                reserved_two_size +
                channelcount_size +
                samplesize_size +
                pre_defined_size +
                reserved_three_size +
                samplerate_size);
        String[] name = new String[]{"全部数据", "length", "type",
                "reserved",
                "data_reference_index",
                "reserved",
                "channelcount",
                "samplesize",
                "pre_defined",
                "reserved",
                "samplerate"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                reserved_one_arr,
                data_reference_index_arr,
                reserved_two_arr,
                channelcount_arr,
                samplesize_arr,
                pre_defined_arr,
                reserved_three_arr,
                samplerate_arr};
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char",
                "char",
                "int",
                "char",
                "int",
                "int",
                "char",
                "char",
                "fixed"};
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
