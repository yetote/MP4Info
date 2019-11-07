package com.yetote.mp4info.util;

import com.yetote.mp4info.model.Box;

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


    public List<Box> getBox(int level, int parentId) {
        return nioReadInfo.getBox(level, parentId);
    }
}
