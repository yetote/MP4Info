package com.yetote.mp4info.util;

public class CharUtil {

    public static String c2Str(byte[] arr) {
        StringBuilder s = new StringBuilder();
        for (byte b : arr) {
            if (b < 0x20) {
                s.append(".");
                continue;
            }
            s.append((char) b);
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

    /**
     * 用于分割原始数据，并保留16进制格式
     *
     * @param arr
     * @return
     */
    public static String changePrimevalData(byte[] arr) {
        StringBuilder s = new StringBuilder();
        String hex = "";
        for (int i = 0; i < arr.length; i++) {
            if (i % 4 == 0) s.append(" ");
            hex = Integer.toHexString(arr[i] & 0xFF);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            s.append(hex);
        }
        return s.toString();
    }
}
