package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;

import java.nio.channels.FileChannel;

public class Mdat extends BasicBox {
    String describe = "此box的数据为音频与视频的真实数据";

    public Mdat(int length) {

    }
    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
