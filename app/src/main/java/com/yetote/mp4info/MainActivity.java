package com.yetote.mp4info;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.yetote.mp4info.adapter.TreeNodeAdapter;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.ReadInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
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
        TreeNode parent = new TreeNode(new Box("mp4", -1, 0, 0, 0, 0));

        root.addChild(parent);
        AndroidTreeView tView = new AndroidTreeView(this, root);
        tView.setDefaultViewHolder(TreeNodeAdapter.class);
        treeView.addView(tView.getView());

        chooseFileBtn.setOnClickListener(v -> {
            String path = pathTv.getText().toString();
            if (path.isEmpty()) {
                path = getExternalFilesDir(Environment.DIRECTORY_MOVIES).getPath() + "/test.mp4";
            }
            readInfo = new ReadInfo(path);
        });
        prepareBtn.setOnClickListener(v -> {
            Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {
                emitter.onNext(readInfo.prepare());
            }).subscribeOn(Schedulers.newThread())
                    .flatMap((Function<Boolean, ObservableSource<List<Box>>>) isPrepare -> {
                        if (isPrepare)
                            return Observable.just(readInfo.getBox(1, 0));
                        else
                            return null;
                    }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(boxes -> {
                        if (boxes != null) {
                            for (int i = 0; i < boxes.size(); i++) {
                                TreeNode child = new TreeNode(boxes.get(i));
                                tView.addNode(parent,child);
                            }
                        }
                    });

        });
    }
}
