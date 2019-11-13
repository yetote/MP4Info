package com.yetote.mp4info;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.yetote.mp4info.adapter.TreeNodeAdapter;
import com.yetote.mp4info.adapter.ViewPagerAdapter;
import com.yetote.mp4info.fragment.DataFragment;
import com.yetote.mp4info.fragment.DescribeFragment;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.ReadInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
    private AndroidTreeView tView;
    private ViewPagerAdapter viewPagerAdapter;
    private ArrayList<Fragment> fragmentArrayList;
    private DescribeFragment describeFragment;
    private DataFragment dataFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        onClick();
    }

    private void onClick() {

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
                    .flatMap((Function<Boolean, ObservableSource<List<Box>>>) isPrepare -> {
                        return isPrepare ? Observable.just(readInfo.getBox(1, 0)) : null;
                    }).subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(boxes -> {
                        if (boxes != null) {
                            for (int i = 0; i < boxes.size(); i++) {
                                TreeNode child = new TreeNode(boxes.get(i));
                                tView.addNode(parent, child);
                            }
                            Toast.makeText(this, "解析完成", Toast.LENGTH_SHORT).show();
                        }
                    });
        });


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
        fragmentArrayList = new ArrayList<>();
        describeFragment = new DescribeFragment();
        dataFragment = new DataFragment();
        fragmentArrayList.add(describeFragment);
        fragmentArrayList.add(dataFragment);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 0, fragmentArrayList);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        root = TreeNode.root();
        parent = new TreeNode(new Box("mp4", -1, 0, 0, 0, 0));

        root.addChild(parent);
        tView = new AndroidTreeView(this, root);
        tView.setDefaultViewHolder(TreeNodeAdapter.class);
//        tView.setSelectionModeEnabled(true);
        tView.setDefaultNodeClickListener(new TreeNode.TreeNodeClickListener() {
            @Override
            public void onClick(TreeNode node, Object value) {
                Box parent = (Box) value;
                if (parent.getLevel() != 0) {
//                    List<Box> boxList = readInfo.readBox(parent);
//                    for (Box child : boxList) {
//                        TreeNode childNode = new TreeNode(child);
//                        Log.e(TAG, "onClick: "+child.toString() );
//                        tView.addNode(node, childNode);
//                    }
//                    tView.addNode(node, child);
                    String[] strings = readInfo.readBox(parent);
//                    Toast.makeText(MainActivity.this, strings[1], Toast.LENGTH_SHORT).show();
                    if (strings != null) {
                        describeFragment.setDescribe(strings[0]);
                        dataFragment.setData(strings[1]);
                    }else {
                        describeFragment.setDescribe("暂无数据");
                        dataFragment.setData("暂无数据");
                    }
                }
            }
        });
        treeView.addView(tView.getView());
    }
}
