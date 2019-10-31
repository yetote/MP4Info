package com.yetote.mp4info;

public class ReadInfo {
    static {
        System.loadLibrary("native-lib");
    }



    public native void show();
}
