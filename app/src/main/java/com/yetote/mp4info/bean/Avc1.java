package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Avc1 extends FullBox {
    private static final String TAG = "Avc1";
    String describe = "该track为Video Track，编码格式为AVC(H.264)";
    private String[] key = new String[]{
            "reserved",
            "data_reference_index",
            "reserved",
            "pre_define",
            "width",
            "height",
            "horizresolution",
            "vertresolution",
            "reserved",
            "frame_count",
            "compressorname",
            "depth",
            "pre_define",
    };
    private String[] introductions = new String[]{
            "预定义",
            "数据引用索引",
            "保留位",
            "预定义",
            "宽",
            "高",
            "横向dpi",
            "纵向dpi",
            "保留位",
            "指定每个样本存储多少帧，video track中固定为1，除非明确的指定了媒体格式",
            "扩展名",
            "位深",
            "预定义",
    };
    private int reserved_one_size = 6;
    private int data_reference_index_size = 2;
    private int pre_define_one_size = 2;
    private int reserved_two_size = 2;
    private int pre_define_two_size = 12;
    private int width_size = 2;
    private int height_size = 2;
    private int horizresolution_size = 4;
    private int vertresolution_size = 4;
    private int reserved_three_size = 4;
    private int frame_count_size = 2;
    private int compressorname_size = 32;
    private int depth_size = 2;
    private int pre_define_three_size = 2;

    private byte[] data_reference_index;
    private byte[] pre_define_one;
    private byte[] reserved_one;
    private byte[] pre_define_two;
    private byte[] width;
    private byte[] height;
    private byte[] horizresolution;
    private byte[] vertresolution;
    private byte[] reserved_two;
    private byte[] reserved_three;
    private byte[] frame_count;
    private byte[] compressorname;
    private byte[] depth;
    private byte[] pre_define_three;


    private byte[] all;

    public Avc1(int length) {
        all = new byte[length];
        data_reference_index = new byte[data_reference_index_size];
        reserved_three = new byte[reserved_three_size];
        pre_define_one = new byte[pre_define_one_size];
        reserved_one = new byte[reserved_one_size];
        pre_define_two = new byte[pre_define_two_size];
        width = new byte[width_size];
        height = new byte[height_size];
        horizresolution = new byte[horizresolution_size];
        vertresolution = new byte[vertresolution_size];
        reserved_two = new byte[reserved_two_size];
        frame_count = new byte[frame_count_size];
        compressorname = new byte[compressorname_size];
        depth = new byte[depth_size];
        pre_define_three = new byte[pre_define_three_size];
    }

    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        box.setOffset(reserved_one_size +
                data_reference_index_size +
                pre_define_one_size +
                reserved_two_size +
                pre_define_two_size +
                width_size +
                height_size +
                horizresolution_size +
                vertresolution_size +
                reserved_three_size +
                frame_count_size +
                compressorname_size +
                depth_size +
                pre_define_three_size);
        Log.e(TAG, "read: offset" + box.getOffset());
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        String[] name = new String[]{"全部数据", "length", "type",
                "reserved", "data_reference_index",
                "pre_define", "reserved",
                "pre_define",
                "width",
                "height",
                "horizresolution",
                "vertresolution",
                "reserved",
                "frame_count",
                "compressorname",
                "depth",
                "pre_define"
        };
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                reserved_one,
                data_reference_index,
                pre_define_one,
                reserved_two,
                pre_define_two,
                width,
                height,
                horizresolution,
                vertresolution,
                reserved_three,
                frame_count,
                compressorname,
                depth,
                pre_define_three,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char",
                "char", "int",
                "char", "char",
                "char",
                "int",
                "int",
                "fixed",
                "fixed",
                "char",
                "int",
                "char",
                "int",
                "char",
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), box.getLength(), fileChannel, name, value, data, type);
    }
}
