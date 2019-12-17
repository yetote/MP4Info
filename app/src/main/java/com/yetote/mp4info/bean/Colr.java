package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Colr extends BasicBox {
    String describe = "Colour information Box,指明了像素长宽比，\n" +
            "hSpacing：像素的相对高度\n" +
            "vSpacing：像素的相对宽度\n";


    private byte[] all;

    public Colr(int length) {
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "length", "type",
                };
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                };
        String[] value = new String[6];
        String[] type = new String[]{"char", "int", "char",
                };
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
