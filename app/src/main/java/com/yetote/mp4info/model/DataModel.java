package com.yetote.mp4info.model;

public class DataModel {
    private String type;
    private String rawData;
    private String decodeData;

    public DataModel(String type, String rawData, String decodeData) {
        this.type = type;
        this.rawData = rawData;
        this.decodeData = decodeData;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getDecodeData() {
        return decodeData;
    }

    public void setDecodeData(String decodeData) {
        this.decodeData = decodeData;
    }
}
