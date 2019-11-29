package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;

import java.nio.channels.FileChannel;

public class Dinf extends BasicBox {
    String describe = "描述媒体信息在track的位置";

    public Dinf(int length) {
    }
    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
