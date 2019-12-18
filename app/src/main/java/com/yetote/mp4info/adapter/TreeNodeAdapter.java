package com.yetote.mp4info.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unnamed.b.atv.model.TreeNode;
import com.yetote.mp4info.R;
import com.yetote.mp4info.model.Box;
import com.yetote.mp4info.util.MP4;

public class TreeNodeAdapter extends TreeNode.BaseNodeViewHolder<Box> {
    private TextView tv;
    private ImageView icon;
    private Context context;
    private static final String TAG = "TreeNodeAdapter";

    public TreeNodeAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View createNodeView(TreeNode node, Box value) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_nodetree_item, null, false);
        tv = v.findViewById(R.id.layout_treenode_item_name);
        v.setPadding(value.getLevel() * 20, 10, 0, 0);
        icon = v.findViewById(R.id.layout_treenode_item_icon);
        if (MP4.getChild(value.getName())) {
            Glide.with(context).load(R.drawable.hide).into(icon);
        } else {
            icon.setVisibility(View.INVISIBLE);
        }
        tv.setText(value.getName());
        return v;
    }


    @Override
    public void toggle(boolean isCheck) {
        Glide.with(context).load(isCheck ? R.drawable.show : R.drawable.hide).into(icon);
    }
}
