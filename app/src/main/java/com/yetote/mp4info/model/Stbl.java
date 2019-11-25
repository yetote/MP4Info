package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import java.nio.channels.FileChannel;

public class Stbl {
    String describe = "该容器又被称为采样列表容器，其中包含了很多用于描述当前track的采样信息的box";

    public Stbl(int length) {
    }
    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel) {
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
