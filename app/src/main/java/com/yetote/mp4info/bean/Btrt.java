package com.yetote.mp4info.bean;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Btrt extends BasicBox {
    String describe = "BitRate Box, 该box指示了该track的比特率\n";

    private int bufferSizeDB_size = 4;
    private int maxBitrate_size = 4;
    private int avgBitrate_size = 4;
    private String[] key = new String[]{
            "bufferSizeDB",
            "maxBitrate",
            "avgBitrate"
    };
    private String[] introductions = new String[]{
            "给出基本流的解码缓冲区的大小（以字节为单位）",
            "给出一秒钟内任何窗口的最大速率（以位/秒为单位）",
            "给出整个演示中的平均速率（以位/秒为单位）"
    };
    private byte[] all;
    private byte[] bufferSizeDB_arr;
    private byte[] maxBitrate_arr;
    private byte[] avgBitrate_arr;

    public Btrt(int length) {
        all = new byte[length];
        bufferSizeDB_arr = new byte[bufferSizeDB_size];
        maxBitrate_arr = new byte[maxBitrate_size];
        avgBitrate_arr = new byte[avgBitrate_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        String[] name = new String[]{"全部数据", "length", "type",
                "bufferSizeDB",
                "maxBitrate",
                "avgBitrate",
        };
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr,
                bufferSizeDB_arr,
                maxBitrate_arr,
                avgBitrate_arr
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char",
                "int",
                "int",
                "int",
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
