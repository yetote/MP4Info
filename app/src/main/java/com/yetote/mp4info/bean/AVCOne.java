package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class AVCOne {
    String describe = "  reserved:预定义\n" +
            "  data_reference_index:数据引用索引\n" +
            "  reserved:保留位\n" +
            "  pre_define:预定义\n" +
            "  width:宽\n" +
            "  height:高\n" +
            "  horizresolution:横向dpi\n" +
            "  vertresolution:纵向dpi\n" +
            "  reserved:保留位\n" +
            "  frame_count:制定每个样本存储多少帧，video track中固定为1，除非明确的指定了媒体格式\n" +
            "  compressorname:Compressorname is a name, for informative purposes. It is formatted in a fixed 32‐byte field, with " +
            "the  first byte  set  to  the  number  of bytes  to  be displayed,  followed by  that number  of  bytes  of " +
            "displayable data, and then padding to complete 32 bytes total (including the size byte). The field " +
            "may be set to 0. \n" +
            "  depth:位深\n" +
            "  pre_define:预定义\n";

    private int reserved_one_size = 6;
    private int data_reference_index_size = 2;
    private int pre_define_one_size = 2;
    private int reserved_two_size = 2;
    private int pre_define_two_size = 12;
    private int width_size = 2;
    private int height_size = 2;
    private int horizresolution_size = 4;
    private int vertresolution_size = 4;
    private int reserved_three_size = 4;
    private int frame_count_size = 2;
    private int compressorname_size = 32;
    private int depth_size = 2;
    private int pre_define_three_size = 2;

    private byte[] data_reference_index;
    private byte[] pre_define_one;
    private byte[] reserved_one;
    private byte[] pre_define_two;
    private byte[] width;
    private byte[] height;
    private byte[] horizresolution;
    private byte[] vertresolution;
    private byte[] reserved_two;
    private byte[] reserved_three;
    private byte[] frame_count;
    private byte[] compressorname;
    private byte[] depth;
    private byte[] pre_define_three;

//    String describe = "Sample Describe 该box描述了编码类型与编码初始化所需需要的参数\n" +
//            "configurationVersion：配置版本\n" +
//            "AVCProfileIndication：在 ISO/IEC 14496-10 中定义的配置文件代码\n" +
//            "profile_compatibility：在 ISO/IEC 14496-10 中定义的profile_IDC和level_IDC的参数集\n" +
//            "AVCLevelIndication：在 ISO/IEC 14496-10 中定义的级别码\n" +
//            "       PS:以上部分均可视为预定义部分\n" +
//            "reservedAndLengthSizeMinusOne：该byte由两部分组成，前1bit为保留位，后1bit用（0,1,3）表示NALUnitLength字段的字节长度（1,2,4）\n" +
//            "reservedAndNumOfSequenceParameterSets：该byte由两部分组成，前1bit为保留位，后一部分指示SPS的集的数量\n" +
//            "sequenceParameterSetLength：指示SPS NAL单元中定义的字节长度\n" +
//            "sequenceParameterSetNALUnit：指示SPS NAL单元\n" +
//            "numOfPictureParameterSets：PPS的数量\n" +
//            "pictureParameterSetLength：指示PPS NAL单元中定义的字节长度\n" +
//            "pictureParameterSetNALUnit：指示PPS NAL单元：\n"
//    private int configurationVersion" 1
//    private int AVCProfileIndication" 1
//    private int profile_compatibility" 1
//    private int AVCLevelIndication" 1
//    private int reservedAndLengthSizeMinusOne" 1
//    private int reservedAndNumOfSequenceParameterSets" 1
//    private int sequenceParameterSetLength" 2
//    //    private int sequenceParameterSetNALUnit_size= 8 * sequenceParameterSetLength
//    private int numOfPictureParameterSets" 1
//    private int pictureParameterSetLength" 2
////    private int pictureParameterSetNALUnit_size= 8 * pictureParameterSetLength
//
//    private byte[] configurationVersion
//    private byte[] AVCProfileIndication
//    private byte[] profile_compatibility
//    private byte[] AVCLevelIndication
//    private byte[] reservedAndLengthSizeMinusOne
//    private byte[] reservedAndNumOfSequenceParameterSets
//    private byte[] sequenceParameterSetLength
//    private byte[] numOfPictureParameterSets
//    private byte[] pictureParameterSetLength
//    //    private int sequenceParameterSetNALUnit = 8 * sequenceParameterSetLength
////    private int pictureParameterSetNALUnit = 8 * pictureParameterSetLength

    private byte[] all;

    public AVCOne(int length) {
        all = new byte[length];
        data_reference_index = new byte[data_reference_index_size];
        reserved_three = new byte[reserved_three_size];
        pre_define_one = new byte[pre_define_one_size];
        reserved_one = new byte[reserved_one_size];
        pre_define_two = new byte[pre_define_two_size];
        width = new byte[width_size];
        height = new byte[height_size];
        horizresolution = new byte[horizresolution_size];
        vertresolution = new byte[vertresolution_size];
        reserved_two = new byte[reserved_two_size];
        frame_count = new byte[frame_count_size];
        compressorname = new byte[compressorname_size];
        depth = new byte[depth_size];
        pre_define_three = new byte[pre_define_three_size];
    }

    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        box.setOffset(reserved_one_size +
                data_reference_index_size +
                pre_define_one_size +
                reserved_two_size +
                pre_define_two_size +
                width_size +
                height_size +
                horizresolution_size +
                vertresolution_size +
                reserved_three_size +
                frame_count_size +
                compressorname_size +
                depth_size +
                pre_define_three_size);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "reserved", "data_reference_index",
                "pre_define", "reserved",
                "pre_define",
                "width",
                "height",
                "horizresolution",
                "vertresolution",
                "reserved",
                "frame_count",
                "compressorname",
                "depth",
                "pre_define"
        };
        byte[][] data = new byte[][]{all, reserved_one, data_reference_index,
                pre_define_one,
                reserved_two,
                pre_define_two,
                width,
                height,
                horizresolution,
                vertresolution,
                reserved_three,
                frame_count,
                compressorname,
                depth,
                pre_define_three,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "char", "int",
                "char", "char",
                "char",
                "int",
                "int",
                "fixed",
                "fixed",
                "char",
                "int",
                "char",
                "int",
                "char",
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), box.getLength(), fileChannel, name, value, data, type);
    }
}
