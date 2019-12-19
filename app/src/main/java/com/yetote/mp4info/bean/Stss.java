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

public class Stss extends FullBox {
    String describe = "Sync Sample Box, 该box用于指示关键帧的索引\n" +
            "\nPS：如果该box不存在，则表示全部为关键帧";

    private String[] key = new String[]{
            "entry_count",
            "sample_number",
    };
    private String[] introductions = new String[]{
            "子表的数量",
            "关键帧的索引",

    };
    private int entry_count_size = 4;
    private int sample_number_size = 4;


    private byte[] all;
    private byte[] entry_count_arr;
    private byte[] sample_number_arr;

    public Stss(int length) {
        all = new byte[length];
        entry_count_arr = new byte[entry_count_size];
        sample_number_arr = new byte[sample_number_size];
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
            String[] name = new String[count + 6];
            byte[][] data = new byte[count + 6][];
            String[] value = new String[count + 6];
            String[] type = new String[count + 6];

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
                name[i + 6] = "sample_number:(" + (i + 1) + ")";
                data[i + 6] = sample_number_arr;
                type[i + 6] = "int";
            }
            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
