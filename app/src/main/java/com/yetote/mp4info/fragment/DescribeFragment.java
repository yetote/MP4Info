package com.yetote.mp4info.fragment;

import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yetote.mp4info.R;

public class DescribeFragment extends Fragment {
    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_describe_layout, null);
        textView = view.findViewById(R.id.describe_tv);
        return view;
    }

    public void setDescribe(SpannableStringBuilder s) {
        textView.setText(s);
    }
}
