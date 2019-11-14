package com.yetote.mp4info.util;

import android.text.SpannableStringBuilder;

import com.yetote.mp4info.model.Box;

import java.util.ArrayList;
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


    public void readBox(SpannableStringBuilder[] builders, Box box) {
        nioReadInfo.readBox(builders, box);
    }

    public ArrayList<Box> readBox(Box box, boolean isChild) {
        return nioReadInfo.readBox(box, isChild);
    }

    public ArrayList<Box> getBox(int level, int parentId) {
        return nioReadInfo.getBox(level, parentId);
    }
}
