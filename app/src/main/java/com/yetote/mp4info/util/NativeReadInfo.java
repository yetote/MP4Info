package com.yetote.mp4info.util;

public class NativeReadInfo {
    static {
        System.loadLibrary("native-lib");
    }
    public static native void readFile(String path);
}
