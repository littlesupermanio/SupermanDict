package com.hhb.supermandict.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhb.supermandict.R;
import com.hhb.supermandict.listener.OnRecyclerViewOnClickListener;
import com.hhb.supermandict.model.NoteBookItem;

import java.util.ArrayList;

/**
 * Created by HoHoibin on 14/01/2018.
 * Email: imhhb1997@gmail.com
 */

public class NoteBookItemAdapter extends RecyclerView.Adapter<NoteBookItemAdapter.ItemViewHolder> {
    private final Context context;
    private final LayoutInflater inflater;
    private ArrayList<NoteBookItem> list;
    private OnRecyclerViewOnClickListener mListener;

    public NoteBookItemAdapter(@NonNull Context context, ArrayList<NoteBookItem> list){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public void setItemClickListener(OnRecyclerViewOnClickListener listener){
        this.mListener = listener;
    }

    @Override
    public NoteBookItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(inflater.inflate(R.layout.note_book_item,parent,false),mListener);
    }

    @Override
    public void onBindViewHolder(NoteBookItemAdapter.ItemViewHolder holder, int position) {
        NoteBookItem item = list.get(position);
        holder.tvOutput.setText(item.getInput() + "\n" + item.getOutput());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvOutput;
        ImageView ivCopy;

        OnRecyclerViewOnClickListener listener;

        public ItemViewHolder(View itemView, final OnRecyclerViewOnClickListener listener) {
            super(itemView);

            tvOutput = (TextView) itemView.findViewById(R.id.text_view_output);
            ivCopy = (ImageView) itemView.findViewById(R.id.image_view_copy);

            this.listener = listener;
            itemView.setOnClickListener(this);

            ivCopy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnSubViewClick(ivCopy,getLayoutPosition());
                }
            });


        }

        @Override
        public void onClick(View view) {
            if (listener != null){
                listener.OnItemClick(view,getLayoutPosition());
            }
        }
    }
}
