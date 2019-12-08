package com.yetote.mp4info.bean;

import com.yetote.mp4info.model.FullBox;

public class Ctts extends FullBox {
    String describe = "Composition Time to Sample Box, chunk偏移容器，该box指明了chunk在文件中的存储位置\n" +
            "version：版本号\n" +
            "flag：标志码\n" +
            "entry_count：chunk offset的数量\n" +
            " sample_count;：给出了每个chunk的偏移量\n" +
            " sample_offset;：给出了每个chunk的偏移量\n";

}
