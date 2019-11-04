//
// Created by yetote on 2019/11/4.
//

#include "ReadFile.h"

using namespace std;

ReadFile::ReadFile(const std::string &path_) : path(path_) {

}


void ReadFile::read() {
    if (path.empty()) {
        LOGE(ReadFile_TAG, "%s:路径为null", __func__);
        return;
    }
    FILE *file = nullptr;
    LOGE(ReadFile_TAG, "%s:path=%s", __func__, path.c_str());
    file = fopen(path.c_str(), "rb+");
    vector<char> dataVec;
    char ftyp[20];
    fread(ftyp, 20, 1, file);
    LOGE(ReadFile_TAG, "%s:前10字节为%s", __func__, ftyp);

    fclose(file);
}

ReadFile::~ReadFile() {

}