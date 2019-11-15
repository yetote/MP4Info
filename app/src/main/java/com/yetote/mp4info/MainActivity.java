package com.yetote.mp4info;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.yetote.mp4info.util.FileUtil;
import com.yetote.mp4info.util.MP4;
import com.yetote.mp4info.util.ReadInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.pm.PackageManager.PERMISSION_DENIED;

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
    SpannableStringBuilder[] builders;
    private static final int FILE_SELECT_CODE = 0x01;
    private static final int PERMISSION_READ_FILE = 0x10;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        onClick();
    }

    private void onClick() {

        chooseFileBtn.setOnClickListener(v -> {
            clear();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_READ_FILE);
            } else {
                chooseFile();
            }
        });

        prepareBtn.setOnClickListener(v -> {
            if (pathTv.getText().toString().isEmpty()) {
                Toast.makeText(this, "请选择文件", Toast.LENGTH_SHORT).show();
                return;
            }
            readInfo = new ReadInfo(pathTv.getText().toString());
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

        tView.setDefaultNodeClickListener((node, value) -> {
            Box box = (Box) value;
            builders[0].clear();
            builders[1].clear();
            if (box.getLevel() != 0) {

                if (MP4.getChild(box.getName())) {
                    ArrayList<Box> list = readInfo.readBox(builders, box, box.isRead());
                    if (list != null) {
                        for (Box b : list) {
                            TreeNode child = new TreeNode(b);
                            tView.addNode(node, child);
                        }
                    }
                    box.setRead(true);
                } else {
                    readInfo.readBox(builders, box);
                }
                describeFragment.setDescribe(builders[0]);
                dataFragment.setData(builders[1]);

            }
        });
    }

    private void chooseFile() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.setType("video/*");
        i.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(i, "Select a File to Upload"), FILE_SELECT_CODE);
    }

    private void initView() {

        pathTv = findViewById(R.id.path_tv);
        chooseFileBtn = findViewById(R.id.choose_file);
        prepareBtn = findViewById(R.id.prepare);
        treeView = findViewById(R.id.treeView);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.vp);
        builders = new SpannableStringBuilder[2];
        builders[0] = new SpannableStringBuilder();
        builders[1] = new SpannableStringBuilder();
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

        treeView.addView(tView.getView());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    String path = FileUtil.findFilePath(this, data.getData());
                    Log.e(TAG, "onActivityResult: " + path);
                    pathTv.setText(path);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_READ_FILE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    chooseFile();
                } else {
                    Toast.makeText(this, "权限被拒绝，无法读取文件", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


    public void clear() {
        pathTv.setText("");
        root.deleteChild(parent);
        parent = new TreeNode(new Box("mp4", -1, 0, 0, 0, 0));
        root.addChild(parent);
    }

}
