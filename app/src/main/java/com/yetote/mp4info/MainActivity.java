package com.yetote.mp4info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.unnamed.b.atv.model.TreeNode;
import com.unnamed.b.atv.view.AndroidTreeView;
import com.unnamed.b.atv.view.TreeNodeWrapperView;
import com.yetote.mp4info.util.ReadInfo;

public class MainActivity extends AppCompatActivity {
    private TextView pathTv;
    private Button chooseFileBtn, prepareBtn;
    private RelativeLayout treeView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ReadInfo readInfo;

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

        TreeNode root = TreeNode.root();
        TreeNode parent = new TreeNode("父节点");
        TreeNode child0 = new TreeNode("子节点1");
        TreeNode child1 = new TreeNode("子节点2");
        parent.addChildren(child0, child1);
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
            readInfo.prepare();
        });
    }
}
