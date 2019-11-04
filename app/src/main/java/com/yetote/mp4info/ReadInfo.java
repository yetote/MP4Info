package com.yetote.mp4info;

public class ReadInfo {

    NIOReadInfo nioReadInfo;

    public ReadInfo(String path) {
        nioReadInfo = new NIOReadInfo(path);
    }

    public void prepare() {
//        NativeReadInfo.readFile(path);
        nioReadInfo.prepare();
    }
}
