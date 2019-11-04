//
// Created by yetote on 2019/11/4.
//

#ifndef MP4INFO_FTYP_H
#define MP4INFO_FTYP_H

#endif //MP4INFO_FTYP_H

#include <string>
/*
 * ftyp为file type
 * 意味着文件格式,包含MP4的一些文件信息
 * */
class Ftyp {
public:
    int getSize() const {
        return size;
    }

    void setSize(int size) {
        Ftyp::size = size;
    }

    const std::string &getMajorBrand() const {
        return major_brand;
    }

    void setMajorBrand(const std::string &majorBrand) {
        major_brand = majorBrand;
    }

    const std::string &getMinorVersion() const {
        return minor_version;
    }

    void setMinorVersion(const std::string &minorVersion) {
        minor_version = minorVersion;
    }

    const std::string &getCompatibleBrands() const {
        return compatible_brands;
    }

    void setCompatibleBrands(const std::string &compatibleBrands) {
        compatible_brands = compatibleBrands;
    }

private:
    std::string describe="ftyp为file type,意味着文件格式,其中包含MP4的一些文件信息";
    int size;
    //协议名称
    std::string major_brand;
    //版本号
    std::string minor_version;
    //兼容的协议
    std::string compatible_brands;
};