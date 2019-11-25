package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import java.nio.channels.FileChannel;

public class Minf {
    String describe = "此box声明了当前track的特征信息";

    public Minf(int length) {
    }
    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel,Box box) {
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
