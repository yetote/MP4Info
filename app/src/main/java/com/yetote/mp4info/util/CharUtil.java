package com.yetote.mp4info.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;

public class CharUtil {
    private static final String TAG = "CharUtil";


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
            i += (arr[j] & 0xff) * Math.pow(16, 2 * (arr.length - 1 - j));
        }
        return i;
    }

    public static long c2long(byte[] arr) {
        long i = 0;
        for (int j = 0; j < arr.length; j++) {
            i += (arr[j] & 0xff) * Math.pow(16, 2 * (arr.length - 1 - j));
        }
        return i;
    }

    public static double c2Fixed(byte[] arr) {
        int length = arr.length;
        double temp = 0.0;
        switch (length) {
            case 2:
                temp = c2Int(arr) / Math.pow(2, 8);
                break;
            case 4:
                Log.e(TAG, "c2Fixed: " + c2Int(arr));
                Log.e(TAG, "c2Fixed: " + Math.pow(2, 16));
                temp = c2Int(arr) / Math.pow(2, 16);
                Log.e(TAG, "c2Fixed: " + temp);
                break;
        }
        return temp;
    }

    public static String c2Time(byte[] data) {
        if (MP4.TIME_SCALE <= 0) {
            byte[] timeScaleArr = new byte[4];
            NIOReadInfo.readItem(timeScaleArr, NIOReadInfo.searchBox("mvhd").getPos() + 20, 4);
            MP4.TIME_SCALE = CharUtil.c2Int(timeScaleArr);
            if (MP4.TIME_SCALE <= 0) {
                return "无法计算具体时间，原始数据为:" + c2Int(data);
            }
        }
        int time = c2Int(data) / MP4.TIME_SCALE;
        int second = time % (60);
        int minute = time % (60 * 60);
        int hour = time / (60 * 60);
        return null;
    }

    public static String c2Duration(byte[] data) {
        if (MP4.TIME_SCALE == 0) {
            return "无法计算具体时间，原始数据为:" + c2Int(data);
        }
        int time = c2Int(data) /;
        int hour = time / (60 * 60)
        return null;
    }

    public static String c2Matrix(byte[] data) {
        return null;
    }
//    public static String c2Matrix(byte[] data) {
//        return null;
//    }

    public static void linkDataString(SpannableStringBuilder builder, String[] name, byte[][] primevalData, String[] value) {
        int start = 0;
        int end = 0;
        for (int i = 0; i < name.length; i++) {
            end = start + name[i].length();
            builder.append(name[i]).append("：\n(")
                    .append(CharUtil.changePrimevalData(primevalData[i]))
                    .append(")\n")
                    .append(value[i])
                    .append("\n")
                    .setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            start,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = builder.length();
        }
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
            if (i != 0 && i % 4 == 0) s.append(" ");
            hex = Integer.toHexString(arr[i] & 0xFF);
            if (hex.length() < 2) {
                hex = "0" + hex;
            }
            s.append(hex);
        }
        return s.toString();
    }

    public static void linkDescribe(SpannableStringBuilder builder, String describe, String[] key, String[] value) {
        builder.append(describe).append("\n\n");
        int start = builder.length();
        int end;
        for (int i = 0; i < key.length; i++) {
            end = start + key[i].length();
            builder.append(key[i]).append("：")
                    .append(value[i])
                    .append("\n")
                    .setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            start,
                            end,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = builder.length();
        }
    }

}
