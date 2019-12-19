package com.yetote.mp4info.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.yetote.mp4info.R;
import com.yetote.mp4info.adapter.DataRvAdapter;
import com.yetote.mp4info.model.DataModel;
import com.yetote.mp4info.util.MyHandler;

import java.util.ArrayList;

public class DataFragment extends Fragment {
    private RecyclerView rv;
    private ArrayList<DataModel> dataList;
    private DataRvAdapter adapter;
    private ImageView waitingIv;
    private static final String TAG = "DataFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_layout, null);
        rv = view.findViewById(R.id.data_rv);
        waitingIv = view.findViewById(R.id.data_waiting_iv);
        Glide.with(getContext()).load(R.drawable.waiting).into(waitingIv);
        dataList = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        MyHandler.getMessage(30, dataList);
        adapter = new DataRvAdapter(dataList, getContext());
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastPosition = -1;

                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    }

                    //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                    //如果相等则说明已经滑动到最后了
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                        getData(30);
                    }
                }
            }
        });
        return view;
    }

    public void clear() {
        dataList.clear();
//        MyHandler.getMessage(30, dataList);
        MyHandler.clear();
        adapter.notifyDataSetChanged();
        waitingIv.setVisibility(View.VISIBLE);
    }

    public void getData(int size) {
        if (waitingIv.getVisibility() == View.VISIBLE) {
            waitingIv.setVisibility(View.GONE);
        }
        int state = MyHandler.getMessage(size, dataList);
        Log.e(TAG, "getData:size " + dataList.size());
        if (state == MyHandler.DATA_FINISH) {
            Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "加载成功", Toast.LENGTH_SHORT).show();
        }
        if (dataList.size() == 0) {
            dataList.add(new DataModel("null", "", ""));
        }
        adapter.notifyDataSetChanged();
    }
}
