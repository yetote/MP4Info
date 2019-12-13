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

public class Sgpd extends FullBox {

    String describe = "SampleGroupDescription Box, 该box用于描述sample group信息\n" +
            "version：版本号\n" +
            "flag：标志码\n" +
            "grouping_type：组名。根据轨道的不同，分组也有不同的组名\n" +
            "default_length：默认长度，指示每个组条目的长度。0表示长度是可变的\n" +
            "default_sample_description_index：默认的样本描述索引(不清楚用途)\n" +
            "entry_count：子分组\n" +
            "description_length：表示单个组条目的长度，如果default_length为0\n" +
            "roll_distance：如果为正值，则表明为了成功解码样本所必需的解码的样本数；负值表示必须全部解码\n";

    private int grouping_type_size = 4;
    private int default_length_size = 4;
    private int default_sample_description_index_size = 4;
    private int entry_count_size = 4;
    private int description_length_size = 4;
    private int roll_distance_size = 2;

    private byte[] all;
    private byte[] grouping_type_arr;
    private byte[] default_length_arr;
    private byte[] default_sample_description_index_arr;
    private byte[] entry_count_arr;
    private byte[] description_length_arr;
    private byte[] roll_distance_arr;

    public Sgpd(int length) {
        all = new byte[length];
        grouping_type_arr = new byte[grouping_type_size];
        default_length_arr = new byte[default_length_size];
        default_sample_description_index_arr = new byte[default_sample_description_index_size];
        entry_count_arr = new byte[entry_count_size];
        description_length_arr = new byte[description_length_size];
        roll_distance_arr = new byte[roll_distance_size];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        try {
            byte[] countArr = new byte[4];
            ByteBuffer buffer = ByteBuffer.allocate(4).order(ByteOrder.nativeOrder());
            //读取entry_count的值
            if (version == 0) {
                fileChannel.position(box.getPos() + 16);
            } else {
                fileChannel.position(box.getPos() + 20);
            }
            fileChannel.read(buffer);
            buffer.flip();
            buffer.get(countArr);
            int count = CharUtil.c2Int(countArr);
            int arrCount = 0;
            String[] name;
            byte[][] data;
            String[] value;
            String[] type;
            if (version == 0) {
                /*
                 * 2表示version==0时，不存在default_length、default_sample_description_index、description_length
                 * 只存在grouping_type、entry_count
                 * 5表示allData、length、type、version、flag
                 * */
                arrCount = count + 2 + 5;
                name = new String[arrCount];
                data = new byte[arrCount][];
                value = new String[arrCount];
                type = new String[arrCount];

                name[5] = "grouping_type";
                data[5] = grouping_type_arr;
                type[5] = "char";

                name[6] = "entry_count";
                data[6] = entry_count_arr;
                type[6] = "int";

                for (int i = 0; i < count; i++) {
                    name[i + 7] = "roll_distance:(" + (i + 1) + ")";
                    data[i + 7] = roll_distance_arr;
                    type[i + 7] = "int";
                }
            } else if (version == 1) {
                //读取default_length的值
                fileChannel.position(box.getPos() + 12);
                fileChannel.read(buffer);
                buffer.flip();
                buffer.get(countArr);
                int defaultLength = CharUtil.c2Int(countArr);
                if (defaultLength == 0) {
                    /*
                     * 2*count表示version==1&&default_length==0时，每个entry的内容有两部分，分别为description_length以及SampleGroupEntry
                     * 3表示grouping_type、 default_length、entry_count
                     * */
                    arrCount = 2 * count + 3 + 5;
                    name = new String[arrCount];
                    data = new byte[arrCount][];
                    value = new String[arrCount];
                    type = new String[arrCount];

                    name[5] = "grouping_type";
                    data[5] = grouping_type_arr;
                    type[5] = "char";

                    name[6] = "default_length";
                    data[6] = default_length_arr;
                    type[6] = "int";

                    name[7] = "entry_count";
                    data[7] = entry_count_arr;
                    type[7] = "int";

                    for (int i = 0; i < count; i++) {
                        name[2 * i + 8] = "description_length:(" + (i + 1) + ")";
                        data[2 * i + 8] = description_length_arr;
                        type[2 * i + 8] = "int";

                        name[2 * i + 9] = "roll_distance:(" + (i + 1) + ")";
                        data[2 * i + 9] = roll_distance_arr;
                        type[2 * i + 9] = "int";
                    }
                } else {
                    /*
                     * 3表示grouping_type、 default_length、entry_count
                     *
                     * */
                    arrCount = count + 3 + 5;
                    name = new String[arrCount];
                    data = new byte[arrCount][];
                    value = new String[arrCount];
                    type = new String[arrCount];

                    name[5] = "grouping_type";
                    data[5] = grouping_type_arr;
                    type[5] = "char";

                    name[6] = "default_length";
                    data[6] = default_length_arr;
                    type[6] = "int";

                    name[7] = "entry_count";
                    data[7] = entry_count_arr;
                    type[7] = "int";

                    for (int i = 0; i < count; i++) {
                        name[i + 8] = "roll_distance:(" + (i + 1) + ")";
                        data[i + 8] = roll_distance_arr;
                        type[i + 8] = "int";
                    }
                }
            } else {
                /*
                 * 3表示grouping_type、default_sample_description_index、entry_count
                 *
                 * */
                arrCount = count + 3 + 5;
                name = new String[arrCount];
                data = new byte[arrCount][];
                value = new String[arrCount];
                type = new String[arrCount];

                name[5] = "grouping_type";
                data[5] = grouping_type_arr;
                type[5] = "char";

                name[6] = "default_sample_description_index";
                data[6] = default_sample_description_index_arr;
                type[6] = "int";

                name[7] = "entry_count";
                data[7] = entry_count_arr;
                type[7] = "int";

                for (int i = 0; i < count; i++) {
                    name[i + 8] = "roll_distance:(" + (i + 1) + ")";
                    data[i + 8] = roll_distance_arr;
                    type[i + 8] = "int";
                }
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

            NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
