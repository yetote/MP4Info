package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import java.nio.channels.FileChannel;

public class Mdat {
    String describe = "此box的数据为音频与视频的真实数据";

    public Mdat(int length) {

    }
    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel,Box box) {
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
