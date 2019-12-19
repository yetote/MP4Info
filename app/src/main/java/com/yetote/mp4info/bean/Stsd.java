package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;
import android.util.Log;

import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.model.FullBox;
import com.yetote.mp4info.util.CharUtil;
import com.yetote.mp4info.util.NIOReadInfo;

import java.lang.reflect.Field;
import java.nio.channels.FileChannel;


public class Stsd extends FullBox {
    String describe = "Sample Describe 该box描述了编码类型与编码初始化所需需要的参数";
    private String[] key = new String[]{
            "entry_count",

    };
    private String[] introductions = new String[]{
            "含有子box的数量",
    };
    private static final String TAG = "Stsd";
    private int version_size = 1;
    private int flag_size = 3;
    private int entry_count_size = 4;
    private byte[] version;
    private byte[] flag;
    private byte[] entry_count;

    private byte[] all;

    public Stsd(int length) {
        all = new byte[length];
        version = new byte[version_size];
        flag = new byte[flag_size];
        entry_count = new byte[entry_count_size];
   }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        box.setOffset(entry_count_size + version_size + flag_size );
        Log.e(TAG, "read:offset " + box.getOffset());
        CharUtil.linkDescribe(builders[0], describe, key, introductions);
        String[] name = new String[]{"全部数据", "length", "type", "version", "flag",
                "entry_count",
        };
        byte[][] data = new byte[][]{all, length != 1 ? length_arr : large_length_arr, type_arr, version, flag,
                entry_count,
        };
        String[] value = new String[name.length];
        String[] type = new String[]{"char", "int", "char", "int", "int",
                "int",
        };
        NIOReadInfo.readBox(builders[1], box.getPos(), length, fileChannel, name, value, data, type);
    }
}
