package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.NIOReadInfo;

import java.nio.channels.FileChannel;

public class Hdlr extends FullBox {
    private static final String TAG = "Hdlr";
    String describe = "hdlr容器描述了track的类型，并记录了媒体的播放过程\n" +
            "version:版本号\n" +
            "flag:标志码\n" +
            "per_define:预定义\n" +
            "handler_type:hdlr类型\n" +
            "       当父容器为mdia时：该值表示track类型包括以下几种值：\n" +
            "               vide：该track为视频\n" +
            "               soun：该track为音频\n" +
            "       当父容器为meta时：该值表示文件的别名\n" +
            "reserved:保留\n" +
            "name:是一个以 \\0 结尾的，可变的扩展hdlr的扩展名，方便理解与调试（该值长度可为0）\n" +
            "ps:该box存在于mdia和meta中\n";
    private int version_size = 1;
    private int flag_size = 3;
    private int per_define_size = 4;
    private int handler_type_size = 4;
    private int reserved_size = 12;
    private int extend_name_size = 0;


    private byte[] version;
    private byte[] flag;
    private byte[] per_define;
    private byte[] handler_type;
    private byte[] reserved;
    private byte[] extend_name;
    private byte[] all;


    public Hdlr(int length) {
        extend_name_size = length - 32;
        Log.e(TAG, "Hdlr: " + extend_name_size);
        version = new byte[version_size];
        flag = new byte[flag_size];
        per_define = new byte[per_define_size];
        handler_type = new byte[handler_type_size];
        reserved = new byte[reserved_size];
        if (extend_name_size != 0) {
            extend_name = new byte[extend_name_size];
        }
        all = new byte[length];
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        String[] name = new String[]{"全部数据", "version", "flag",
                "per_define",
                "handler_type",
                "reserved",
                "name"};
        byte[][] data = new byte[][]{all, version, flag,
                per_define,
                handler_type,
                reserved,
                extend_name,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "int",
                "char",
                "char",
                "char",
                "null",
        };
        if (extend_name_size != 0) {
            type[6] = "char";
        }
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }

}
