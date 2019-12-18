package com.yetote.mp4info.util;

import android.util.Log;

import com.yetote.mp4info.model.DataModel;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class MyHandler {
    private static int count = 0;
    private static BlockingDeque<DataModel> queue = new LinkedBlockingDeque<>();

    public static final int DATA_CONTINUE = 0x0001;
    public static final int DATA_PAUSE = 0x0002;
    public static final int DATA_RESUME = 0x0003;
    public static final int DATA_FLUSH = 0x0004;
    public static final int DATA_FINISH = 0x0005;
    private static boolean pause = false;
    private static boolean finish = false;
    private static final String TAG = "MyHandler";
    public static boolean stop = false;

    public static void pushMessage(int messageCode, DataModel model) {
        if (model == null) {
            return;
        }
        switch (messageCode) {
            case DATA_CONTINUE:
                queue.add(model);
                Log.e(TAG, "pushMessage: push data");
                break;
            case DATA_PAUSE:
                break;
            case DATA_RESUME:

                break;
            case DATA_FLUSH:
                clear();
                break;
            case DATA_FINISH:
                finish = true;
                queue.add(model);
                break;
            default:
                break;
        }
    }

    public static int getMessage(int count, ArrayList<DataModel> models) {
        int max = Math.min(count, queue.size());
        if (queue.isEmpty() && finish) {
            return DATA_FINISH;
        }
        for (int i = 0; i < max; i++) {
            try {
                models.add(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (queue.isEmpty() && finish) {
                return DATA_FINISH;
            }
        }
        return DATA_CONTINUE;
    }


    public static void clear() {
        count = 0;
        pause = false;
        finish = false;
        stop = false;
        queue.clear();
    }
}
