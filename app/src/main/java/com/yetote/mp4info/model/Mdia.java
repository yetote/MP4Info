package com.yetote.mp4info.model;

import android.text.SpannableStringBuilder;

import java.nio.channels.FileChannel;

public class Mdia {
    String describe = "此track的媒体信息，该容器必须包括mdhd（MediaHeader）、hdlr（Handler Reference）、minf（MediaInformation）";

    public Mdia(int length) {

    }
    public void read(SpannableStringBuilder[] builders, int pos, int length, FileChannel fileChannel,Box box) {
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
