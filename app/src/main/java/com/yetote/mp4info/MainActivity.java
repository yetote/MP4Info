package com.yetote.mp4info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.unnamed.b.atv.view.TreeNodeWrapperView;
import com.yetote.mp4info.util.ReadInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView pathTv;
    private Button chooseFileBtn, prepareBtn;
    private RelativeLayout treeView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ReadInfo readInfo;
    TreeNode root;
    TreeNode parent;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

//        String path = getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath() + "/test.mp4";

//        readInfo.prepare();
    }

    private void initView() {

        pathTv = findViewById(R.id.path_tv);
        chooseFileBtn = findViewById(R.id.choose_file);
        prepareBtn = findViewById(R.id.prepare);
        treeView = findViewById(R.id.treeView);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.vp);

        tabLayout.addTab(tabLayout.newTab().setText("描述"), true);
        tabLayout.addTab(tabLayout.newTab().setText("数据"));

        root = TreeNode.root();
        TreeNode parent = new TreeNode("mp4");

        root.addChild(parent);
        AndroidTreeView tView = new AndroidTreeView(this, root);
        treeView.addView(tView.getView());

        chooseFileBtn.setOnClickListener(v -> {
            String path = pathTv.getText().toString();
            if (path.isEmpty()) {
                path = getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath() + "/test.mp4";
            }
            readInfo = new ReadInfo(path);
        });
        prepareBtn.setOnClickListener(v -> {
            Observable.create((ObservableOnSubscribe<Boolean>) emitter -> emitter.onNext(readInfo.prepare()))
                    .subscribeOn(Schedulers.newThread())
                    .flatMap((Function<Boolean, ObservableSource<List<String>>>) rst -> {
                        if (rst) return Observable.just(readInfo.getBoxName(1, 0));
                        else return null;
                    }).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(strings -> {
                        if (strings != null) {
                            for (int i = 0; i < strings.size(); i++) {
                                TreeNode child = new TreeNode(strings.get(i));
                                parent.addChildren(child);
                            }
                        }
                    });

        });
    }
}
