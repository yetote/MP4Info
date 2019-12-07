package com.yetote.mp4info.adapter;

import android.content.Context;
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

    public DataRvAdapter(ArrayList<DataModel> list, Context context) {
        this.list = list;
        this.context = context;
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_data_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder vh = (MyViewHolder) holder;
        vh.getTypeTv().setText(list.get(position).getType());
        vh.getRawTv().setText(list.get(position).getRawData());
        vh.getDecodeTv().setText(list.get(position).getDecodeData());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
