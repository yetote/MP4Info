package com.yetote.mp4info.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yetote.mp4info.R;
import com.yetote.mp4info.model.DataModel;

import java.util.ArrayList;

public class DataRvAdapter extends RecyclerView.Adapter {
    private ArrayList<DataModel> list;
    private Context context;
    private SpannableStringBuilder builder;
    public static final int DATA_RAW = 0X0001;
    public static final int DATA_DECODE = 0X0002;

    public DataRvAdapter(ArrayList<DataModel> list, Context context) {
        this.list = list;
        this.context = context;
        builder = new SpannableStringBuilder();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rawTv;
        private TextView typeTv;
        private TextView decodeTv;

        public TextView getTypeTv() {
            return typeTv;
        }

        public void setTypeTv(TextView typeTv) {
            this.typeTv = typeTv;
        }

        public TextView getDecodeTv() {
            return decodeTv;
        }

        public void setDecodeTv(TextView decodeTv) {
            this.decodeTv = decodeTv;
        }

        public TextView getRawTv() {
            return rawTv;
        }

        public void setRawTv(TextView rawTv) {
            this.rawTv = rawTv;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            typeTv = itemView.findViewById(R.id.item_data_type);
            rawTv = itemView.findViewById(R.id.item_data_raw_data);
            decodeTv = itemView.findViewById(R.id.item_data_decode_data);
        }
    }

    class RawDataViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public RawDataViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_raw_data_tv);
        }

        public TextView getTextView() {
            return textView;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == DATA_DECODE) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_data_layout, parent, false));
        } else {
            return new RawDataViewHolder(LayoutInflater.from(context).inflate(R.layout.item_data_layout, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder vh = (MyViewHolder) holder;
            builder.clear();
            builder.append("名称: ")
                    .append(list.get(position).getType())
                    .setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0,
                            3,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            vh.getTypeTv().setText(builder);
            builder.clear();
            builder.append("原始: ")
                    .append(list.get(position).getRawData())
                    .setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0,
                            3,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            vh.getRawTv().setText(builder);
            builder.clear();
            builder.append("转换: ")
                    .append(list.get(position).getDecodeData())
                    .setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                            0,
                            3,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            vh.getDecodeTv().setText(builder);
        } else {
            RawDataViewHolder vh = (RawDataViewHolder) holder;
            vh.getTextView().setText(list.get(position).getRawData());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType().equals("raw"))
            return DATA_RAW;
        else return DATA_DECODE;
    }
}
