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
    private String describe = "stts:Time to sample atom。即采样时间容器。该值描述了视频的dts\n" +
            "length：长度\n" +
            "type：atom 名\n" +
            "version：版本号\n" +
            "flag：标志码\n" +
            "entry_count：子表的数目(一对sample_count和sample_delta是一张子表)\n" +
            "sample_count：具有相同sample_delta的sample数目\n" +
            "sample_delta：相对上一sample的dts增量\n";
    private byte[] all;
    private static final String TAG = "Stts";

    public Stts(int length) {
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
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
                name[i * 2 + 6] = "sample_count" + (i + 1);
                data[i * 2 + 6] = new byte[4];
                type[i * 2 + 6] = "int";
                name[i * 2 + 7] = "sample_delta" + (i + 1);
                data[i * 2 + 7] = new byte[4];
                type[i * 2 + 7] = "int";
            }
            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}