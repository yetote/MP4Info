package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Colr extends BasicBox {
    String describe = "Colour information Box,颜色信息";

    private String[] key = new String[]{
            "color_type_size",
    };
    private String[] introductions = new String[]{
            "指示了所提供的颜色信息的类型",
    };
    private int color_type_size = 4;
    private int colour_primaries_size = 2;
    private int transfer_characteristics_size = 2;
    private int matrix_coefficients_size = 2;
    private int full_range_flag_and_reserved_size = 1;

    private byte[] all;
    private byte[] color_type_arr;
    private byte[] colour_primaries_arr;
    private byte[] transfer_characteristics_arr;
    private byte[] matrix_coefficients_arr;
    private byte[] full_range_flag_and_reserved_arr;

    public Colr(int length) {
        all = new byte[length];
        color_type_arr = new byte[color_type_size];
        colour_primaries_arr = new byte[colour_primaries_size];
        transfer_characteristics_arr = new byte[transfer_characteristics_size];
        matrix_coefficients_arr = new byte[matrix_coefficients_size];
        full_range_flag_and_reserved_arr = new byte[full_range_flag_and_reserved_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        try {
            byte[] colorTypeArr = new byte[4];
            ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
            fileChannel.position(box.getPos() + 12);
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(colorTypeArr);
            String colorType = CharUtil.c2Str(colorTypeArr);
            String[] name;
            byte[][] data;
            String[] value;
            String[] type;
            switch (colorType) {
                case "nclx":
                    name = new String[]{"全部数据", "length", "type",
                            "colour_type",
                            "colour_primaries",
                            "transfer_characteristics",
                            "matrix_coefficients",
                            "full_range_flag_and_reserved",
                    };
                    data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                            color_type_arr,
                            colour_primaries_arr,
                            transfer_characteristics_arr,
                            matrix_coefficients_arr,
                            full_range_flag_and_reserved_arr,
                    };
                    value = new String[name.length];
                    type = new String[]{"char", "int", "char",
                            "char",
                            "int",
                            "int",
                            "int",
                            "int",
                    };
                    NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
                    break;
                default:
                    name = new String[]{"全部数据", "length", "type",
                            "colour_type",
                    };
                    data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                            color_type_arr,
                    };
                    value = new String[name.length];
                    type = new String[]{"char", "int", "char",
                            "char",
                    };
                    NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
                    break;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
