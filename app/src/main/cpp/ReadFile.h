//
// Created by yetote on 2019/11/4.
//

#ifndef MP4INFO_READFILE_H
#define MP4INFO_READFILE_H

#include <string>
#include <vector>
#include "LogUtils.h"
#define ReadFile_TAG "ReadFile"

#include "model/Ftyp.h"
class ReadFile {
public:
    ReadFile(const std::string &path);

    virtual ~ReadFile();

    void read();
private:
    std::string path;

};


#endif //MP4INFO_READFILE_H
