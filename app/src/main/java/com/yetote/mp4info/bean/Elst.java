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

public class Elst extends FullBox {
    String describe = "Edit List Box, 该box存储了track的时间偏离量\n" +
            "version：版本号\n" +
            "flag：标志码\n" +
            "entry_count：子表的数目\n" +
            "segment_duration：指明此edit段的时长，用mvhd的time_scale进行转换\n" +
            "media_time：指明此edit段的起始时间，用mvhd的time_scale进行转换\n" +
            "media_rate_integer：如果该值为0，则表明画面时暂停的;如果该值为-1(0xffffff)，则表明该elst为空\n" +
            "media_rate_fraction：始终为0，文档上未注明其含义\n";

    private int entry_count_size = 4;
    private int segment_duration_size = 2;
    private int media_time_size = 2;
    private int media_rate_integer_size = 2;
    private int media_rate_fraction_size = 2;

    private byte[] all;
    private byte[] entry_count_arr;
    private byte[] segment_duration_arr;
    private byte[] media_time_arr;
    private byte[] media_rate_integer_arr;
    private byte[] media_rate_fraction_arr;

    public Elst(int length) {
        entry_count_arr = new byte[entry_count_size];
        media_rate_integer_arr = new byte[media_rate_integer_size];
        media_rate_fraction_arr = new byte[media_rate_fraction_size];
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        if (version == 0) {
            segment_duration_size = 4;
            media_time_size = 4;
        } else {
            segment_duration_size = 8;
            media_time_size = 8;
        }
        try {
            byte[] countArr = new byte[4];
            ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
            fileChannel.position(box.getPos() + 12);
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(countArr);
            int count = CharUtil.c2Int(countArr);
            String[] name = new String[4 * count + 6];
            byte[][] data = new byte[4 * count + 6][];
            String[] value = new String[4 * count + 6];
            String[] type = new String[4 * count + 6];

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
                name[i * 4 + 6] = "segment_duration(" + (i + 1) + ")";
                data[i * 4 + 6] = new byte[segment_duration_size];
                type[i * 4 + 6] = "int";

                name[i * 4 + 7] = "media_time(" + (i + 1) + ")";
                data[i * 4 + 7] = new byte[media_time_size];
                type[i * 4 + 7] = "int";

                name[i * 4 + 8] = "media_rate_integer(" + (i + 1) + ")";
                data[i * 4 + 8] = new byte[2];
                type[i * 4 + 8] = "int";

                name[i * 4 + 9] = "media_rate_fraction(" + (i + 1) + ")";
                data[i * 4 + 9] = new byte[2];
                type[i * 4 + 9] = "int";
            }


            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
