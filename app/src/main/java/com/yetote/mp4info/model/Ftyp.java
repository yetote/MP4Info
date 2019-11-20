package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Ftyp {
    //协议名称
    byte[] major_brand;
    //版本号
    byte[] minor_version;
    //兼容的协议
    byte[] compatible_brands;
    byte[] all;
    private static final String TAG = "Ftyp";
    String describe = "ftyp为file type,意味着文件格式,其中包含MP4的一些文件信息,其中包含三部分:\nmajor_brand(协议名称)\nminor_version(版本号)\ncompatible_brands(兼容的协议)";
    int length;

    int major_brand_size = 4;
    int minor_version_size = 4;
    int compatible_brands_size = 12;

    public Ftyp(int length) {
        major_brand = new byte[major_brand_size];
        minor_version = new byte[minor_version_size];
        all = new byte[length];
        compatible_brands_size = length - minor_version_size - major_brand_size - 8;
        compatible_brands = new byte[compatible_brands_size];
        Log.e(TAG, "Ftyp: " + compatible_brands_size);
    }


    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel) {
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "major_brand", "minor_version", "compatible_brands"};
        byte[][] data = new byte[][]{all, major_brand, minor_version, compatible_brands};
        String[] value = new String[4];
        String[] type = new String[]{"char",
                "char",
                "int",
                "char"};
        NIOReadInfo.readBox(builders[1], pos, length, fileChannel, name, value, data, type);
    }
}
