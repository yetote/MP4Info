//
// Created by ether on 2019/11/1.
//

#ifndef MP4INFO_LOGUTILS_H
#define MP4INFO_LOGUTILS_H

#endif //MP4INFO_LOGUTILS_H

#include <android/log.h>

#define LOGE(LOG_TAG, ...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)