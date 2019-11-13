package com.yetote.mp4info.util;

public class CharUtil {

    public static String c2Str(byte[] arr) {
        StringBuilder s = new StringBuilder();
        for (byte b : arr) {
            if (b < 0x20) {
                s.append(".");
                continue;
            }
            s.append(b);
        }
        return s.toString();
    }

    public static int c2Int(byte[] arr) {
        int i = 0;
        for (int j = 0; j < arr.length; j++) {
            i += (arr[j] & 0xff) * Math.pow(16, 6 - 2 * j);
        }
        return i;
    }
}
