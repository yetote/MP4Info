package com.yetote.mp4info.util;

import com.yetote.mp4info.model.Ftyp;
import com.yetote.mp4info.model.Mdat;
import com.yetote.mp4info.model.Mdhd;
import com.yetote.mp4info.model.Mdia;
import com.yetote.mp4info.model.Moov;
import com.yetote.mp4info.model.Mvhd;
import com.yetote.mp4info.model.Tkhd;
import com.yetote.mp4info.model.Trak;

import java.util.HashMap;
import java.util.Map;

public class MP4 {
    public static final Map<String, String> MP4_MAP = new HashMap<String, String>();
    public static final Map<String, Boolean> CHILD_MAP = new HashMap<>();
    public static int TIME_SCALE = 0;

    static {
        MP4_MAP.put("ftyp", Ftyp.class.getName());
        MP4_MAP.put("moov", Moov.class.getName());
        MP4_MAP.put("mvhd", Mvhd.class.getName());
        MP4_MAP.put("trak", Trak.class.getName());
        MP4_MAP.put("tkhd", Tkhd.class.getName());
        MP4_MAP.put("mdia", Mdia.class.getName());
        MP4_MAP.put("mdhd", Mdhd.class.getName());

        CHILD_MAP.put("ftyp", false);
        CHILD_MAP.put("moov", true);
        CHILD_MAP.put("mvhd", false);
        CHILD_MAP.put("trak", true);
        CHILD_MAP.put("tkhd", false);
        CHILD_MAP.put("mdia", true);
        CHILD_MAP.put("mdhd", false);
    }

    public static String getValue(String key) {
        return MP4_MAP.get(key);
    }

    public static boolean getChild(String key) {
        return CHILD_MAP.get(key);
    }
}
