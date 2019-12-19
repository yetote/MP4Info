package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Ctts extends FullBox {
    String describe = "Composition Time to Sample Box, 该box指定了每个sample的Composition Time与Decode Time的时间差\n" +
            "       \nPS:如果该box不存在，则表示视频中无B帧\n";


    private String[] key = new String[]{
            "version",
            "flag",
            "entry_count",
            "sample_count",
            "sample_offset",
    };
    private String[] introductions = new String[]{
            "version：版本号\n" +
                    "       0：sample_offset为有符号32位整数\n" +
                    "       1：sample_offset为无符号32位整数\n",
            "标志码",
            "表的条目数",
            "具有相同偏移量的sample",
            "CT(n)与DT(n)的偏移量，具体公式为:CT(n)=DT(n)+CTTS(n)",
    };

    private int entry_count_size = 4;
    private int sample_count_size = 4;
    private int sample_offset_size = 4;

    private byte[] all;
    private byte[] entry_count_arr;
    private byte[] sample_count_arr;
    private byte[] sample_offset_arr;

    public Ctts(int length) {
        all = new byte[length];
        entry_count_arr = new byte[entry_count_size];
        sample_count_arr = new byte[sample_count_size];
        sample_offset_arr = new byte[sample_offset_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        try {
            byte[] countArr = new byte[4];
            ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
            fileChannel.position(box.getPos() + 12);
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(countArr);
            int count = CharUtil.c2Int(countArr);
            String[] name = new String[2 * count + 6];
            byte[][] data = new byte[2 * count + 6][];
            String[] value = new String[2 * count + 6];
            String[] type = new String[2 * count + 6];

            name[0] = "全部数据";
            data[0] = all;
            type[0] = "char";

            name[1] = "length";
            data[1] = length_arr;
            type[1] = "int";

            name[2] = "type";
            data[2] = type_arr;
            type[2] = "char";

            name[3] = "version";
            data[3] = version_arr;
            type[3] = "int";

            name[4] = "flag";
            data[4] = flag_arr;
            type[4] = "int";

            name[5] = "entry_count";
            data[5] = entry_count_arr;
            type[5] = "int";

            for (int i = 0; i < count; i++) {
                name[i * 2 + 6] = "sample_count:(" + (i + 1) + ")";
                data[i * 2 + 6] = sample_count_arr;
                type[i * 2 + 6] = "int";

                name[i * 2 + 7] = "sample_offset:(" + (i + 1) + ")";
                data[i * 2 + 7] = sample_offset_arr;
                type[i * 2 + 7] = "long";
            }
            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
