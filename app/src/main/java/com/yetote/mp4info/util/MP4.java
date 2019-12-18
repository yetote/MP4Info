package com.yetote.mp4info.util;

import com.yetote.mp4info.bean.Avc1;
import com.yetote.mp4info.bean.Avcc;
import com.yetote.mp4info.bean.Clap;
import com.yetote.mp4info.bean.Co64;
import com.yetote.mp4info.bean.Colr;
import com.yetote.mp4info.bean.Ctts;
import com.yetote.mp4info.bean.Dinf;
import com.yetote.mp4info.bean.Dref;
import com.yetote.mp4info.bean.Edts;
import com.yetote.mp4info.bean.Elst;
import com.yetote.mp4info.bean.Esds;
import com.yetote.mp4info.bean.Ftyp;
import com.yetote.mp4info.bean.Hdlr;
import com.yetote.mp4info.bean.Ilst;
import com.yetote.mp4info.bean.Mdat;
import com.yetote.mp4info.bean.Mdhd;
import com.yetote.mp4info.bean.Mdia;
import com.yetote.mp4info.bean.Meta;
import com.yetote.mp4info.bean.Minf;
import com.yetote.mp4info.bean.Moov;
import com.yetote.mp4info.bean.Mp4a;
import com.yetote.mp4info.bean.Mp4v;
import com.yetote.mp4info.bean.Mvhd;
import com.yetote.mp4info.bean.Pasp;
import com.yetote.mp4info.bean.Sbgp;
import com.yetote.mp4info.bean.Sgpd;
import com.yetote.mp4info.bean.Smhd;
import com.yetote.mp4info.bean.Stbl;
import com.yetote.mp4info.bean.Stco;
import com.yetote.mp4info.bean.Stsc;
import com.yetote.mp4info.bean.Stsd;
import com.yetote.mp4info.bean.Stss;
import com.yetote.mp4info.bean.Stsz;
import com.yetote.mp4info.bean.Stts;
import com.yetote.mp4info.bean.Tkhd;
import com.yetote.mp4info.bean.Trak;
import com.yetote.mp4info.bean.Udta;
import com.yetote.mp4info.bean.Vmhd;

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
        MP4_MAP.put("avc1", Avc1.class.getName());              CHILD_MAP.put("avc1", true);
        MP4_MAP.put("avcC", Avcc.class.getName());              CHILD_MAP.put("avcC", false);
        MP4_MAP.put("stts", Stts.class.getName());              CHILD_MAP.put("stts", false);
        MP4_MAP.put("stsz", Stsz.class.getName());              CHILD_MAP.put("stsz", false);
        MP4_MAP.put("stsc", Stsc.class.getName());              CHILD_MAP.put("stsc", false);
        MP4_MAP.put("stco", Stco.class.getName());              CHILD_MAP.put("stco", false);
        MP4_MAP.put("stss", Stss.class.getName());              CHILD_MAP.put("stss", false);
        MP4_MAP.put("ctts", Ctts.class.getName());              CHILD_MAP.put("ctts", false);
        MP4_MAP.put("smhd", Smhd.class.getName());              CHILD_MAP.put("smhd", false);
        MP4_MAP.put("edts", Edts.class.getName());              CHILD_MAP.put("edts", true);
        MP4_MAP.put("elst", Elst.class.getName());              CHILD_MAP.put("elst", false);
        MP4_MAP.put("sgpd", Sgpd.class.getName());              CHILD_MAP.put("sgpd", false);
        MP4_MAP.put("sbgp", Sbgp.class.getName());              CHILD_MAP.put("sbgp", false);
        MP4_MAP.put("mp4a", Mp4a.class.getName());              CHILD_MAP.put("mp4a", true);
        MP4_MAP.put("esds", Esds.class.getName());              CHILD_MAP.put("esds", false);
        MP4_MAP.put("co64", Co64.class.getName());              CHILD_MAP.put("co64", false);
        MP4_MAP.put("clap", Clap.class.getName());              CHILD_MAP.put("clap", false);
        MP4_MAP.put("colr", Colr.class.getName());              CHILD_MAP.put("colr", false);
        MP4_MAP.put("pasp", Pasp.class.getName());              CHILD_MAP.put("pasp", false);
        MP4_MAP.put("udta", Udta.class.getName());              CHILD_MAP.put("udta", true);
        MP4_MAP.put("meta", Meta.class.getName());              CHILD_MAP.put("meta", true);
        MP4_MAP.put("ilst", Ilst.class.getName());              CHILD_MAP.put("ilst", false);
        MP4_MAP.put("mp4v", Avc1.class.getName());              CHILD_MAP.put("mp4v", true);
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
