package com.yetote.mp4info.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class FileUtil {
    public static String findFilePath(Context context, Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow("_data");
            if (cursor.moveToFirst()) {
                String s = cursor.getString(column_index);
                cursor.close();
                return s;
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
}
