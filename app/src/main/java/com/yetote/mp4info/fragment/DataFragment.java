package com.yetote.mp4info.fragment;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yetote.mp4info.R;
import com.yetote.mp4info.adapter.DataRvAdapter;
import com.yetote.mp4info.model.DataModel;
import com.yetote.mp4info.util.MyHandler;
import com.yetote.mp4info.util.NIOReadInfo;

import java.util.ArrayList;
import java.util.Arrays;

public class DataFragment extends Fragment {
    private RecyclerView rv;
    private ArrayList<DataModel> dataList;
    private DataRvAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_layout, null);
        rv = view.findViewById(R.id.data_rv);
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
                        int state = MyHandler.getMessage(30, dataList);
                        Toast.makeText(getContext(), "加载成功", Toast.LENGTH_SHORT).show();
                        if (state == MyHandler.DATA_FINISH) {
                            Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
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
    }
}
