package com.yetote.mp4info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ReadInfo readInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String path = getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath() + "/test.mp4";
        readInfo = new ReadInfo(path);
        readInfo.prepare();
    }
}
