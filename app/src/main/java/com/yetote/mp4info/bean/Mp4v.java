package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Mp4v extends BasicBox {
    String describe = "Sample Describe 该box描述了编码类型与编码初始化所需需要的参数\n" +
            "reserved：保留\n" +
            "data_reference_index：数据引用索引\n" +
            "pre_defined：预定义\n" +
            "reserved：保留\n" +
            "pre_defined：预定义\n" +
            "width：最大宽\n" +
            "height：最大高\n" +
            "horizresolution：水平分辨率，16.16定点数\n" +
            "vertresolution：垂直分辨率，16.16定点数\n" +
            "reserved：保留 \n" +
            "frame_count：每个样本中有多少帧\n" +
            "compressorname：扩展名\n" +
            "depth：颜色位深，从文档中看出只有0x0018这一种\n" +
            "pre_defined：预定义\n";
    private int reserved_one_size = 6;
    private int data_reference_index_size = 2;
    private int pre_defined_one_size = 2;
    private int reserved_two_size = 2;
    private int pre_defined_two_size = 12;
    private int width_size = 2;
    private int height_size = 2;
    private int horizresolution_size = 4;
    private int vertresolution_size = 4;
    private int reserved_three_size = 4;
    private int frame_count_size = 2;
    private int compressorname_size = 32;
    private int depth_size = 2;
    private int pre_defined_three_size = 2;

    private byte[] reserved_one_arr;
    private byte[] data_reference_index_arr;
    private byte[] pre_defined_one_arr;
    private byte[] reserved_two_arr;
    private byte[] pre_defined_two_arr;
    private byte[] width_arr;
    private byte[] height_arr;
    private byte[] horizresolution_arr;
    private byte[] vertresolution_arr;
    private byte[] reserved_three_arr;
    private byte[] frame_count_arr;
    private byte[] compressorname_arr;
    private byte[] depth_arr;
    private byte[] pre_defined_three_arr;

    private byte[] all;

    public Mp4v(int length) {
        all = new byte[length];
        reserved_one_arr = new byte[reserved_one_size];
        data_reference_index_arr = new byte[data_reference_index_size];
        pre_defined_one_arr = new byte[pre_defined_one_size];
        reserved_two_arr = new byte[reserved_two_size];
        pre_defined_two_arr = new byte[pre_defined_two_size];
        width_arr = new byte[width_size];
        height_arr = new byte[height_size];
        horizresolution_arr = new byte[horizresolution_size];
        vertresolution_arr = new byte[vertresolution_size];
        reserved_three_arr = new byte[reserved_three_size];
        frame_count_arr = new byte[frame_count_size];
        compressorname_arr = new byte[compressorname_size];
        depth_arr = new byte[depth_size];
        pre_defined_three_arr = new byte[pre_defined_three_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
//        box.setOffset(entry_count_size + version_size + flag_size)
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type",
                "reserved",
                "data_reference_index",
                "pre_defined",
                "reserved",
                "pre_defined",
                "width",
                "height",
                "horizresolution",
                "vertresolution",
                "reserved",
                "frame_count",
                "compressorname",
                "depth",
                "pre_defined",
        };
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                reserved_one_arr,
                data_reference_index_arr,
                pre_defined_one_arr,
                reserved_two_arr,
                pre_defined_two_arr,
                width_arr,
                height_arr,
                horizresolution_arr,
                vertresolution_arr,
                reserved_three_arr,
                frame_count_arr,
                compressorname_arr,
                depth_arr,
                pre_defined_three_arr,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char",
                "char",
                "int",
                "char",
                "char",
                "char",
                "fixed",
                "fixed",
                "fixed",
                "fixed",
                "char",
                "int",
                "char",
                "int",
                "char",
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
