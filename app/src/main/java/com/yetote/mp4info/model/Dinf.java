package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import java.nio.channels.FileChannel;

public class Dinf {
    String describe = "描述媒体信息在track的位置";

    public Dinf(int length) {
    }
    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel,Box box) {
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
