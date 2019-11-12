package com.yetote.mp4info.model;

import java.util.HashMap;
import java.util.Map;

public class MP4 {
    public static final Map<String, String> MP4_MAP = new HashMap<String, String>();

    static {
        MP4_MAP.put("ftyp", Ftyp.class.getName());
    }

    public static String getValue(String key) {
        return MP4_MAP.get(key);
    }
}
