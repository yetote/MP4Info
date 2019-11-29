package com.yetote.mp4info.bean;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.BasicBox;
import com.yetote.mp4info.model.Box;

import java.nio.channels.FileChannel;

public class Mdia extends BasicBox {
    String describe = "此track的媒体信息，该容器必须包括mdhd（MediaHeader）、hdlr（Handler Reference）、minf（MediaInformation）";

    public Mdia(int length) {

    }
    @Override
    public void read(SpannableStringBuilder[] builders, FileChannel fileChannel, Box box) {
        super.read(builders, fileChannel, box);
        builders[0].append(this.describe);
        builders[1].append("暂无数据");
    }
}
