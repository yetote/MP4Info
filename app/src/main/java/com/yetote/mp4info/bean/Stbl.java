package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;

import java.nio.channels.FileChannel;

public class Stbl extends BasicBox {
    String describe = "该容器又被称为采样列表容器，其中包含了很多用于描述当前track的采样信息的box";

    public Stbl(int length) {
    }

    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);

        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
