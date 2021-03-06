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

public class Co64 extends FullBox {
    String describe = "Chunk offset Box, chunk偏移容器，该box指明了chunk在文件中的存储位置\n" +
            "\nPS：stco和co64都属于 chunk offset table，区别只是stco的chunk_offset是32bit;co64是64bit\n";
    private String[] key = new String[]{
            "version",
            "flag",
            "entry_count",
            "chunk_offset"
    };
    private String[] introductions = new String[]{
            "版本号",
            "标志码",
            "chunk offset的数量",
            "给出了每个chunk的偏移量"
    };
    private int entry_count_size = 4;
    private int chunk_offset_size = 8;


    private byte[] all;
    private byte[] entry_count_arr;
    private byte[] chunk_offset_arr;

    public Co64(int length) {
        all = new byte[length];
        entry_count_arr = new byte[entry_count_size];
        chunk_offset_arr = new byte[chunk_offset_size];
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
                name[i + 6] = "chunk_offset(" + (i + 1) + "):";
                data[i + 6] = new byte[8];
                type[i + 6] = "int";
            }
            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
