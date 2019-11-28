package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Avcc {

    String describe = "Sample Describe 该box描述了编码类型与编码初始化所需需要的参数\n" +
            "configurationVersion：配置版本\n" +
            "AVCProfileIndication：在 ISO/IEC 14496-10 中定义的配置文件代码\n" +
            "profile_compatibility：在 ISO/IEC 14496-10 中定义的profile_IDC和level_IDC的参数集\n" +
            "AVCLevelIndication：在 ISO/IEC 14496-10 中定义的级别码\n" +
            "       PS:以上部分均可视为预定义部分\n" +
            "reservedAndLengthSizeMinusOne：该byte由两部分组成，前1bit为保留位，后1bit用（0,1,3）表示NALUnitLength字段的字节长度（1,2,4）\n" +
            "reservedAndNumOfSequenceParameterSets：该byte由两部分组成，前1bit为保留位，后一部分指示SPS的集的数量\n" +
            "sequenceParameterSetLength：指示NAL单元中定义的 SPS 字节长度\n" +
            "sequenceParameterSetNALUnit：指示NAL单元的SPS \n" +
            "numOfPictureParameterSets：PPS的数量\n" +
            "pictureParameterSetLength：指示NAL单元中定义的PPS 字节长度\n" +
            "pictureParameterSetNALUnit：指示NAL单元的PPS ：\n";
    private int configurationVersion_size = 1;
    private int AVCProfileIndication_size = 1;
    private int profile_compatibility_size = 1;
    private int AVCLevelIndication_size = 1;
    private int reservedAndLengthSizeMinusOne_size = 1;
    private int reservedAndNumOfSequenceParameterSets_size = 1;
    private int sequenceParameterSetLength_size = 2;
    private int sequenceParameterSetNALUnit_size = 1;
    private int numOfPictureParameterSets_size = 1;
    private int pictureParameterSetLength_size = 2;
    private int pictureParameterSetNALUnit_size = 1;

    private byte[] configurationVersion;
    private byte[] AVCProfileIndication;
    private byte[] profile_compatibility;
    private byte[] AVCLevelIndication;
    private byte[] reservedAndLengthSizeMinusOne;
    private byte[] reservedAndNumOfSequenceParameterSets;
    private byte[] sequenceParameterSetLength;
    private byte[] numOfPictureParameterSets;
    private byte[] pictureParameterSetLength;
    private byte[] sequenceParameterSetNALUnit;
    private byte[] pictureParameterSetNALUnit;

    private byte[] all;

    public Avcc(int length) {
        all = new byte[length];
        configurationVersion = new byte[configurationVersion_size];
        AVCProfileIndication = new byte[AVCProfileIndication_size];
        profile_compatibility = new byte[profile_compatibility_size];
        AVCLevelIndication = new byte[AVCLevelIndication_size];
        reservedAndLengthSizeMinusOne = new byte[reservedAndLengthSizeMinusOne_size];
        reservedAndNumOfSequenceParameterSets = new byte[reservedAndNumOfSequenceParameterSets_size];
        sequenceParameterSetLength = new byte[sequenceParameterSetLength_size];
        numOfPictureParameterSets = new byte[numOfPictureParameterSets_size];
        pictureParameterSetLength = new byte[pictureParameterSetLength_size];
        sequenceParameterSetNALUnit = new byte[sequenceParameterSetNALUnit_size];
        pictureParameterSetNALUnit = new byte[pictureParameterSetNALUnit_size];
    }

    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel, Box box) {
//        box.setOffset(entry_count_size + version_size + flag_size)
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据",
                "configurationVersion", "AVCProfileIndication",
                "profile_compatibility", "AVCLevelIndication",
                "reservedAndLengthSizeMinusOne",
                "reservedAndNumOfSequenceParameterSets",
                "sequenceParameterSetLength",
                "sequenceParameterSetNALUnit",
                "numOfPictureParameterSets",
                "pictureParameterSetLength",
                "pictureParameterSetNALUnit",
        };
        byte[][] data = new byte[][]{all,
                configurationVersion, AVCProfileIndication,
                profile_compatibility, AVCLevelIndication,
                reservedAndLengthSizeMinusOne,
                reservedAndNumOfSequenceParameterSets,
                sequenceParameterSetLength,
                sequenceParameterSetNALUnit,
                numOfPictureParameterSets,
                pictureParameterSetLength,
                pictureParameterSetNALUnit,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char",
                "int", "int",
                "int", "int",
                "int",
                "int",
                "next is mult(8,last)",
                "char",
                "int",
                "next is mult(8,last)",
                "char",
        };
        NIOReadInfo.readBox(builders[1], pos, length, fileChannel, name, value, data, type);
    }
}
