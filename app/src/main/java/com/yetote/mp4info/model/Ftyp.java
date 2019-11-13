package com.yetote.mp4info.model;

import android.util.Log;

import com.yetote.mp4info.util.CharUtil;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class Ftyp {
    String describe = "ftyp为file type,意味着文件格式,其中包含MP4的一些文件信息,其中包含三部分:\nmajor_brand(协议名称)\nminor_version(版本号)\ncompatible_brands(兼容的协议)";
    //协议名称
    byte[] major_brand;
    //版本号
    byte[] minor_version;
    //兼容的协议
    byte[] compatible_brands;
    int length;

    int major_brand_size = 4;
    int minor_version_size = 4;
    int compatible_brands_size = 12;

    public Ftyp() {
//        major_brand = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
//        minor_version = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
//        compatible_brands = ByteBuffer.allocate(16).order(ByteOrder.nativeOrder());

        major_brand = new byte[4];
        minor_version = new byte[4];
        compatible_brands = new byte[12];
    }


    public String[] read(int pos, int length, FileChannel fileChannel) {
        ByteBuffer ftypBuffer = ByteBuffer.allocate(length).order(ByteOrder.nativeOrder());
        try {
            String describe = this.describe;
            fileChannel.position(pos);
            fileChannel.read(ftypBuffer);

            ftypBuffer.flip();
            byte[] all = new byte[length];
            ftypBuffer.get(all);
            String all_str = CharUtil.changePrimevalData(all);

            ftypBuffer.position(8);
            ftypBuffer.get(major_brand);
            String major_brand_str = CharUtil.c2Str(major_brand);

            ftypBuffer.get(minor_version);
            int minor_version_str = CharUtil.c2Int(minor_version);

            ftypBuffer.get(compatible_brands);
            String compatible_brands_str = CharUtil.c2Str(compatible_brands);

            ftypBuffer.clear();
            String data = "全部数据 : " + all_str + "\n"
                    + "major_brand : ("+ CharUtil.changePrimevalData(major_brand)+")"+ major_brand_str + "\n"
                    + "minor_version : ("+ CharUtil.changePrimevalData(minor_version)+")"+ minor_version_str + "\n"
                    + "compatible_brands : ("+ CharUtil.changePrimevalData(compatible_brands)+")"+ compatible_brands_str + "\n";
            return new String[]{describe, data};
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
