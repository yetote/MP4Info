package com.yetote.mp4info.util;

import com.yetote.mp4info.model.AVCOne;
import com.yetote.mp4info.model.Avcc;
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

    //    @formatter:off
    static {

        MP4_MAP.put("ftyp", Ftyp.class.getName());              CHILD_MAP.put("ftyp", false);
        MP4_MAP.put("mdat", Mdat.class.getName());              CHILD_MAP.put("mdat", false);
        MP4_MAP.put("moov", Moov.class.getName());              CHILD_MAP.put("moov", true);
        MP4_MAP.put("mvhd", Mvhd.class.getName());              CHILD_MAP.put("mvhd", false);
        MP4_MAP.put("trak", Trak.class.getName());              CHILD_MAP.put("trak", true);
        MP4_MAP.put("tkhd", Tkhd.class.getName());              CHILD_MAP.put("tkhd", false);
        MP4_MAP.put("mdia", Mdia.class.getName());              CHILD_MAP.put("mdia", true);
        MP4_MAP.put("mdhd", Mdhd.class.getName());              CHILD_MAP.put("mdhd", false);
        MP4_MAP.put("hdlr", Hdlr.class.getName());              CHILD_MAP.put("hdlr", false);
        MP4_MAP.put("minf", Minf.class.getName());              CHILD_MAP.put("minf", true);
        MP4_MAP.put("vmhd", Vmhd.class.getName());              CHILD_MAP.put("vmhd", false);
        MP4_MAP.put("dinf", Dinf.class.getName());              CHILD_MAP.put("dinf", true);
        MP4_MAP.put("dref", Dref.class.getName());              CHILD_MAP.put("dref", false);
        MP4_MAP.put("stbl", Stbl.class.getName());              CHILD_MAP.put("stbl", true);
        MP4_MAP.put("stsd", Stsd.class.getName());              CHILD_MAP.put("stsd", true);
        MP4_MAP.put("avc1", AVCOne.class.getName());            CHILD_MAP.put("avc1", true);
        MP4_MAP.put("avcC", Avcc.class.getName());              CHILD_MAP.put("avcC", false);

    }
    //    @formatter:on

    public static String getValue(String key) {
        if (MP4_MAP.containsKey(key)) {
            return MP4_MAP.get(key);
        } else {
            return "";
        }
    }

    public static boolean getChild(String key) {
        if (CHILD_MAP.containsKey(key)) {
            return CHILD_MAP.get(key);
        } else {
            return false;
        }
    }
}
