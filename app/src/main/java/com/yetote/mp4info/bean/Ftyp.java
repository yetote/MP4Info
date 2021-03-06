package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Ftyp extends BasicBox {
    //协议名称
    byte[] major_brand;
    //版本号
    byte[] minor_version;
    //兼容的协议
    byte[] compatible_brands;
    byte[] all;
    private static final String TAG = "Ftyp";
    String describe = "ftyp为file type,意味着文件格式,其中包含MP4的一些文件信息";

    int major_brand_size = 4;
    int minor_version_size = 4;
    int compatible_brands_size = 12;
    private String[] key = new String[]{
            "major_brand",
            "nminor_version",
            "compatible_brands"
    };
    private String[] introductions = new String[]{
            "协议名称",
            "版本号",
            "兼容的协议"
    };

    public Ftyp(int length) {
        major_brand = new byte[major_brand_size];
        minor_version = new byte[minor_version_size];
        all = new byte[length];
        compatible_brands_size = length - minor_version_size - major_brand_size - 8;
        compatible_brands = new byte[compatible_brands_size];
        Log.e(TAG, "Ftyp: " + compatible_brands_size);
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        Log.e(TAG, "read: " + length);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        String[] name = new String[]{"全部数据", "length", "type",
                "major_brand",
                "minor_version",
                "compatible_brands"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                major_brand,
                minor_version,
                compatible_brands};
        String[] value = new String[6];
        String[] type = new String[]{"char", "int", "char",
                "char",
                "int",
                "char"};
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
