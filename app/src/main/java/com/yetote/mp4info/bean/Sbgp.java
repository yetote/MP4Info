package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.bugly.crashreport.crash.CrashDetailBean;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;

public class Sbgp extends FullBox {
    String describe = "Sample to Group Box, 该box用于查找样本所属的样本组以及相关描述\n" +
            "version：版本号\n" +
            "flag：标志码\n" +
            "grouping_type：组名。根据轨道的不同，分组也有不同的组名\n" +
            "grouping_type_parameter：分组的子类型（只有version==1时才会出现）\n" +
            "entry_count：子分组\n" +
            "sample_count：描述具有相同样本组描述的连续样本数\n" +
            "group_description_index： is an integer that gives the index of the sample group entry which \n" +
            "describes  the  samples in  this group.  The index  ranges  from  1  to  the  number  of  sample group \n" +
            "entries  in  the  SampleGroupDescription  Box,  or  takes  the  value  0  to  indicate  that  this \n" +
            "sample is a member of no group of this type. \n";

    private int grouping_type_size = 4;
    private int grouping_type_parameter_size = 4;
    private int entry_count_size = 4;
    private int sample_count_size = 4;
    private int group_description_index_size = 4;

    private byte[] all;
    private byte[] grouping_type_arr;
    private byte[] grouping_type_parameter_arr;
    private byte[] entry_count_arr;
    private byte[] sample_count_arr;
    private byte[] group_description_index_arr;


    public Sbgp(int length) {
        all = new byte[length];
        grouping_type_arr = new byte[grouping_type_size];
        grouping_type_parameter_arr = new byte[grouping_type_parameter_size];
        entry_count_arr = new byte[entry_count_size];
        sample_count_arr = new byte[sample_count_size];
        group_description_index_arr = new byte[group_description_index_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        try {
            byte[] countArr = new byte[4];
            ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
            fileChannel.position(box.getPos() + 16 + ((version == 1) ? 4 : 0));
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(countArr);
            int count = CharUtil.c2Int(countArr);
            String[] name = new String[2 * count + 7 + ((version == 1) ? 1 : 0)];
            byte[][] data = new byte[2 * count + 7 + ((version == 1) ? 1 : 0)][];
            String[] value = new String[2 * count + 7 + ((version == 1) ? 1 : 0)];
            String[] type = new String[2 * count + 7 + ((version == 1) ? 1 : 0)];

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

            name[5] = "grouping_type";
            data[5] = new byte[4];
            type[5] = "char";

            int offset = 0;
            if (version == 1) {
                offset = 1;
                name[6] = "grouping_type_parameter";
                data[6] = new byte[4];
                type[6] = "int";
            }
            name[6 + offset] = "entry_count";
            data[6 + offset] = new byte[4];
            type[6 + offset] = "int";


            for (int i = 0; i < count; i++) {
                name[2 * i + 7 + offset] = "sample_count:(" + (i + offset + 1) + ")";
                data[2 * i + 7 + offset] = new byte[4];
                type[2 * i + 7 + offset] = "int";

                name[2 * i + 8 + offset] = "group_description_index:(" + (i + offset + 1) + ")";
                data[2 * i + 8 + offset] = new byte[4];
                type[2 * i + 8 + offset] = "int";
            }
            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}