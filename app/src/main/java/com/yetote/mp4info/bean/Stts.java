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

public class Stts extends FullBox {
    private String describe = "Time to Sample Box。即采样时间容器。该值描述了视频的dts";

    private String[] key = new String[]{
            "entry_count",
            "sample_count",
            "sample_delta",
    };
    private String[] introductions = new String[]{
            "子表的数目(一对sample_count和sample_delta是一张子表)",
            "具有相同sample_delta的sample数目",
            "相对上一sample的dts增量",
    };
    private byte[] all;
    private static final String TAG = "Stts";

    public Stts(int length) {
        if (length>=1000) length=1000;
        all = new byte[length];
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
            Log.e(TAG, "read: " + count);
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
            data[5] = new byte[4];
            type[5] = "int";

            for (int i = 0; i < count; i++) {
                name[i * 2 + 6] = "sample_count(" + (i + 1)+"):";
                data[i * 2 + 6] = new byte[4];
                type[i * 2 + 6] = "int";
                name[i * 2 + 7] = "sample_delta(" + (i + 1)+"):";
                data[i * 2 + 7] = new byte[4];
                type[i * 2 + 7] = "int";
            }
            NIOReadInfo.readBox(builders[1], box.getPos(),  Math.min(length, 1000), fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
