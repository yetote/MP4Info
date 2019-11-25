package com.yetote.mp4info.util;

import com.yetote.mp4info.model.Dinf;
import com.yetote.mp4info.model.Dref;
import com.yetote.mp4info.model.Ftyp;
import com.yetote.mp4info.model.Hdlr;
import com.yetote.mp4info.model.Mdat;
import com.yetote.mp4info.model.Mdhd;
import com.yetote.mp4info.model.Mdia;
import com.yetote.mp4info.model.Minf;
import com.yetote.mp4info.model.Moov;
import com.yetote.mp4info.model.Mvhd;
import com.yetote.mp4info.model.Stbl;
import com.yetote.mp4info.model.Stsd;
import com.yetote.mp4info.model.Tkhd;
import com.yetote.mp4info.model.Trak;
import com.yetote.mp4info.model.Vmhd;

import java.util.HashMap;
import java.util.Map;

public class MP4 {
    public static final Map<String, String> MP4_MAP = new HashMap<String, String>();
    public static final Map<String, Boolean> CHILD_MAP = new HashMap<>();
    public static int TIME_SCALE = 0;

    static {
        MP4_MAP.put("ftyp", Ftyp.class.getName());
        MP4_MAP.put("mdat", Mdat.class.getName());
        MP4_MAP.put("moov", Moov.class.getName());
        MP4_MAP.put("mvhd", Mvhd.class.getName());
        MP4_MAP.put("trak", Trak.class.getName());
        MP4_MAP.put("tkhd", Tkhd.class.getName());
        MP4_MAP.put("mdia", Mdia.class.getName());
        MP4_MAP.put("mdhd", Mdhd.class.getName());
        MP4_MAP.put("hdlr", Hdlr.class.getName());
        MP4_MAP.put("minf", Minf.class.getName());
        MP4_MAP.put("vmhd", Vmhd.class.getName());
        MP4_MAP.put("dinf", Dinf.class.getName());
        MP4_MAP.put("dref", Dref.class.getName());
        MP4_MAP.put("stbl", Stbl.class.getName());
        MP4_MAP.put("stsd", Stsd.class.getName());

        CHILD_MAP.put("ftyp", false);
        CHILD_MAP.put("mdat", false);
        CHILD_MAP.put("moov", true);
        CHILD_MAP.put("mvhd", false);
        CHILD_MAP.put("trak", true);
        CHILD_MAP.put("tkhd", false);
        CHILD_MAP.put("mdia", true);
        CHILD_MAP.put("mdhd", false);
        CHILD_MAP.put("hdlr", false);
        CHILD_MAP.put("minf", true);
        CHILD_MAP.put("vmhd", false);
        CHILD_MAP.put("dinf", true);
        CHILD_MAP.put("dref", false);
        CHILD_MAP.put("stbl", true);
        CHILD_MAP.put("stsd", false);
    }

    public static String getValue(String key) {
        return MP4_MAP.get(key);
    }

    public static boolean getChild(String key) {
        return CHILD_MAP.get(key);
    }
}
