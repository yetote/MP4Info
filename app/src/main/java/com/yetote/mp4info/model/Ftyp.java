package com.yetote.mp4info.model;

public class Ftyp {
    String describe = "ftyp为file type,意味着文件格式,其中包含MP4的一些文件信息";
    //协议名称
    String major_brand;
    //版本号
    String minor_version;
    //兼容的协议
    String compatible_brands;
    int length;

    public String getMajor_brand() {
        return major_brand;
    }

    public void setMajor_brand(String major_brand) {
        this.major_brand = major_brand;
    }

    public String getMinor_version() {
        return minor_version;
    }

    public void setMinor_version(String minor_version) {
        this.minor_version = minor_version;
    }

    public String getCompatible_brands() {
        return compatible_brands;
    }

    public void setCompatible_brands(String compatible_brands) {
        this.compatible_brands = compatible_brands;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
