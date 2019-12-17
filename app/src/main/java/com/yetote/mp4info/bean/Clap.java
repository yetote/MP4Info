package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Clap extends BasicBox {
    String describe = " Clean Aperture Box,不知道干啥用的，也不知道咋翻译。clap box 中有四对值，使用分数N/D表示\n" +
            "       PS：对于horizOff和vertOff，分母必须为正值\n" +
            "           对于cleanApertureWidth和cleanApertureHeight，分子分母都必须为正值\n" +
            "cleanApertureWidthN：clean Aperture 宽度（分子）\n" +
            "cleanApertureWidthD：clean Aperture 宽度（分母）\n"+
            "cleanApertureHeightN：clean Aperture 高度（分子）\n" +
            "cleanApertureHeightD：clean Aperture 高度（分母）\n"+
            "horizOffN： a fractional number which defines the horizontal offset of clean" +
            "aperture centre minus(width‐1)/2.Typically 0（分子）\n" +
            "horizOffD：horizOffN： a fractional number which defines the horizontal offset of clean" +
            "aperture centre minus(width‐1)/2.Typically 0（分母）\n"+
            "vertOffN： a fractional number which defines the vertical offset of clean aperture" +
            "centre minus (height‐1)/2 Typically 0. （分子）\n" +
            "vertOffD： a fractional number which defines the vertical offset of clean aperture" +
            "centre minus (height‐1)/2. Typically 0.（分母） \n";

    private int cleanApertureWidthN_size = 4;
    private int cleanApertureWidthD_size = 4;
    private int cleanApertureHeightN_size = 4;
    private int cleanApertureHeightD_size = 4;
    private int horizOffN_size = 4;
    private int horizOffD_size = 4;
    private int vertOffN_size = 4;
    private int vertOffD_size = 4;

    private byte[] all;
    private byte[] cleanApertureWidthN_arr;
    private byte[] cleanApertureWidthD_arr;
    private byte[] cleanApertureHeightN_arr;
    private byte[] cleanApertureHeightD_arr;
    private byte[] horizOffN_arr;
    private byte[] horizOffD_arr;
    private byte[] vertOffN_arr;
    private byte[] vertOffD_arr;

    public Clap(int length) {
        all = new byte[length];
        cleanApertureWidthN_arr = new byte[cleanApertureWidthN_size];
        cleanApertureWidthD_arr = new byte[cleanApertureWidthD_size];
        cleanApertureHeightN_arr = new byte[cleanApertureHeightN_size];
        cleanApertureHeightD_arr = new byte[cleanApertureHeightD_size];
        horizOffN_arr = new byte[horizOffN_size];
        horizOffD_arr = new byte[horizOffD_size];
        vertOffN_arr = new byte[vertOffN_size];
        vertOffD_arr = new byte[vertOffD_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type",
                "cleanApertureWidthN",
                "cleanApertureWidthD",
                "cleanApertureHeightN",
                "cleanApertureHeightD",
                "horizOffN",
                "horizOffD",
                "vertOffN",
                "vertOffD"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                cleanApertureWidthN_arr,
                cleanApertureWidthD_arr,
                cleanApertureHeightN_arr,
                cleanApertureHeightD_arr,
                horizOffN_arr,
                horizOffD_arr,
                vertOffN_arr,
                vertOffD_arr};
        String[] value = new String[6];
        String[] type = new String[]{"char", "int", "char",
                "int",
                "int",
                "int",
                "int",
                "int",
                "int",
                "int",
                "int"};
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
