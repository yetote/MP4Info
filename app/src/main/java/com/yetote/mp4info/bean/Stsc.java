package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Stsc extends FullBox {
    String describe = "Sample to Chunk Box, 该box描述了chunk和sample的关系";
    private String[] key = new String[]{
            "entry_count",
            "first_chunk",
            "sample_per_chunk",
            "sample_description_index",
    };
    private String[] introductions = new String[]{
            "chunk的数量",
            "如果该值不连续，则表明省略的chunk含有和上一个chunk相同的sample数量",
            "sample_description_index：is an integer that gives the index of the sample entry that " +
                    "describes the samples in this chunk.The index ranges from 1 to the number of sample entries in " +
                    "the Sample Description Box",
    };

    private int entry_count_size = 4;
    private int first_chunk_size = 4;
    private int sample_per_chunk_size = 4;
    private int sample_description_index_size = 4;

    private byte[] all;
    private byte[] entry_count_arr;
    private byte[] first_chunk_arr;
    private byte[] sample_per_chunk_arr;
    private byte[] sample_description_index_arr;

    public Stsc(int length) {
        all = new byte[length];
        entry_count_arr = new byte[entry_count_size];
        first_chunk_arr = new byte[first_chunk_size];
        sample_per_chunk_arr = new byte[sample_per_chunk_size];
        sample_description_index_arr = new byte[sample_description_index_size];
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
            String[] name = new String[3 * count + 6];
            byte[][] data = new byte[3 * count + 6][];
            String[] value = new String[3 * count + 6];
            String[] type = new String[3 * count + 6];

            name[0] = "全部数据";
            data[0] = new byte[length];
            type[0] = "char";

            name[1] = "length";
            data[1] = new byte[4];
            type[1] = "int";

            name[2] = "type";
            data[2] = new byte[4];
            type[2] = "char";

            name[3] = "version";
            data[3] = new byte[3];
            type[3] = "int";

            name[4] = "flag";
            data[4] = new byte[1];
            type[4] = "int";

            name[5] = "entry_count";
            data[5] = new byte[4];
            type[5] = "int";

            for (int i = 0; i < count; i++) {
                name[i * 3 + 6] = "first_chunk" + (i + 1);
                data[i * 3 + 6] = new byte[4];
                type[i * 3 + 6] = "int";

                name[i * 3 + 7] = "sample_per_chunk" + (i + 1);
                data[i * 3 + 7] = new byte[4];
                type[i * 3 + 7] = "int";

                name[i * 3 + 8] = "sample_description_index" + (i + 1);
                data[i * 3 + 8] = new byte[4];
                type[i * 3 + 8] = "int";
            }
            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
