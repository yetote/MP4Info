#include <jni.h>
#include <string>
#include <vector>
#include <unistd.h>
#include "LogUtils.h"
#include "ReadFile.h"
#include "Test.h"

#define native_lib_TAG "native_lib"
JavaVM *jvm;
//ReadFile *read;
Test *test;

void readFile(JNIEnv *env, jobject thiz, jstring path_) {
    std::string path = env->GetStringUTFChars(path_, JNI_FALSE);
    LOGE(native_lib_TAG, "%s:path=%s", __func__, path.c_str());
//    read = new ReadFile(path);
//    read->read();
//    sleep(600);
    test = new Test;
    std::vector<int> temp = {1,4,2,3};
    test->medianSlidingWindow(temp, 4);
}


JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }

    jclass jlz = env->FindClass("com/yetote/mp4info/util/NativeReadInfo");
    JNINativeMethod methods[]{
            {"readFile", "(Ljava/lang/String;)V", (void *) &readFile},

    };
    env->RegisterNatives(jlz, methods, sizeof(methods) / sizeof(methods[0]));
    return JNI_VERSION_1_6;
}
