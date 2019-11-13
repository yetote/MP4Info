package com.yetote.mp4info.util;

import com.yetote.mp4info.model.Ftyp;

import java.util.HashMap;
import java.util.Map;

public class MP4 {
    public static final Map<String, String> MP4_MAP = new HashMap<String, String>();
    public static final Map<String, Boolean> CHILD_MAP = new HashMap<>();

    static {
        MP4_MAP.put("ftyp", Ftyp.class.getName());
        MP4_MAP.put("mdat", Ftyp.class.getName());

        CHILD_MAP.put("ftyp", false);
        CHILD_MAP.put("moov", true);
    }

    public static String getValue(String key) {
        return MP4_MAP.get(key);
    }

    public static boolean getChild(String key) {
        return CHILD_MAP.get(key);
    }
}
