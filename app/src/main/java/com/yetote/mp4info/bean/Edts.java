package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Edts extends BasicBox {
    String describe = "此box包含了elst box，用于指明某一track的时间偏移量";
    private byte[] all;

    public Edts(int length) {
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type"};
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr};
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char"};

        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
