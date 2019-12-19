package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Stsz extends FullBox {
    private String describe = "Sample size Box。用于表示每一个sample的大小";
    private String[] key = new String[]{
            "sample_size",
            "sample_count",
            "entry_size",
    };
    private String[] introductions = new String[]{
            "如果不为0，则表示sample的大小相等都为该值；如果为0，则表示大小不相等，具体大小由entry_size指定",
            "sample个数",
            "sample的大小",
    };
    private int sample_size_size = 4;
    private int sample_count_size = 4;
    private int entry_size_size = 4;

    private byte[] all;
    private byte[] sample_size_arr;
    private byte[] sample_count_arr;
    private byte[] entry_size_arr;

    private static final String TAG = "Stts";

    public Stsz(int length) {
        all = new byte[length];
        sample_size_arr = new byte[sample_size_size];
        sample_count_arr = new byte[sample_count_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        try {
            ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
            fileChannel.position(box.getPos() + 12);
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(sample_size_arr);
            int sampleSize = CharUtil.c2Int(sample_size_arr);
            Log.e(TAG, "read: " + sampleSize);


            String[] name;
            byte[][] data;
            String[] value;
            String[] type;
            int sampleCount = 0;
            if (sampleSize == 0) {
                buffer.clear();
                fileChannel.read(buffer);
                buffer.flip();
                buffer.get(sample_count_arr);
                sampleCount = CharUtil.c2Int(sample_count_arr);
                name = new String[7 + sampleCount];
                data = new byte[7 + sampleCount][];
                value = new String[7 + sampleCount];
                type = new String[7 + sampleCount];
            } else {
                name = new String[7];
                data = new byte[7][];
                value = new String[7];
                type = new String[7];
            }

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

            name[5] = "sample_size";
            data[5] = new byte[4];
            type[5] = "int";

            name[6] = "sample_count";
            data[6] = new byte[4];
            type[6] = "int";

            for (int i = 0; i < sampleCount; i++) {
                name[i + 7] = "entry_size" + (i + 1);
                data[i + 7] = new byte[4];
                type[i + 7] = "int";
            }
            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
