package com.jermyn.hima.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jermyn.hima.R;
import com.jermyn.hima.list.ListItem;
import com.ximalaya.ting.android.opensdk.model.album.Album;

import java.util.ArrayList;
import java.util.List;

public class RecommendAdapter extends BaseQuickAdapter<ListItem, BaseViewHolder> {

    public RecommendAdapter() {
        super(0);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, ListItem s) {
    }

    @NonNull
    @Override
    protected BaseViewHolder onCreateDefViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateDefViewHolder(parent, viewType);
    }
}
