package com.yetote.mp4info.util;

import java.util.List;

public class ReadInfo {

    NIOReadInfo nioReadInfo;

    public ReadInfo(String path) {
        nioReadInfo = new NIOReadInfo(path);
    }

    public boolean prepare() {
//        NativeReadInfo.readFile(path);
        return nioReadInfo.prepare();
    }


    public List<String> getBoxName(int level, int parentId) {
        return nioReadInfo.getBoxName(level, parentId);
    }
}
