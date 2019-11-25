package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class AVCOne {
    String describe = "Sample Describe 该box描述了编码类型与编码初始化所需需要的参数\n" +
            "configurationVersion：在 ISO/IEC 14496-10 中定义的配置文件代码\n" +
            "AVCProfileIndication：\n" +
            "profile_compatibility：\n" +
            "AVCLevelIndication：\n" +
            "reservedAndLengthSizeMinusOne：\n" +
            "reservedAndNumOfSequenceParameterSets：\n" +
            "sequenceParameterSetLength：\n" +
            "sequenceParameterSetNALUnit：\n" +
            "numOfPictureParameterSets：\n" +
            "pictureParameterSetLength：\n" +
            "pictureParameterSetNALUnit：含有子box的数量\n" +
            "pictureParameterSetNALUnit：含有子box的数量\n" +
            "pictureParameterSetNALUnit：含有子box的数量\n" +
            "pictureParameterSetNALUnit：含有子box的数量\n" +
            ;
    private int configurationVersion = 1;
    private int AVCProfileIndication = 1;
    private int profile_compatibility = 1;
    private int AVCLevelIndication = 1;
    private int reservedAndLengthSizeMinusOne = 1;
    private int reservedAndNumOfSequenceParameterSets = 1;
    private int sequenceParameterSetLength = 2;
    private int sequenceParameterSetNALUnit = 8 * sequenceParameterSetLength;
    private int numOfPictureParameterSets = 1;
    private int pictureParameterSetLength = 2;
    private int pictureParameterSetNALUnit = 8 * pictureParameterSetLength;
    private int configurationVersion = 1;
    private int configurationVersion = 1;
    private int configurationVersion = 1;
    private int configurationVersion = 1;
    private int configurationVersion = 1;
    private int configurationVersion = 1;


    private byte[] all;

    public AVCOne(int length) {
        all = new byte[length];
    }

    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel, Box box) {
        box.setOffset(entry_count_size + version_size + flag_size);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "version", "flag",
                "entry_count",
//                "reserved_size",
//                "data_reference_index_size"
        };
        byte[][] data = new byte[][]{all, version, flag,
                entry_count,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "int",
                "int",
        };
        NIOReadInfo.readBox(builders[1], pos, length, fileChannel, name, value, data, type);
    }
}
