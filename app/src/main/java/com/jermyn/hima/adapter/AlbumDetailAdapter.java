package com.jermyn.hima.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.BaseLoadMoreModule;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jermyn.hima.R;
import com.jermyn.hima.adapter.holder.ListItemViewHolder;
import com.jermyn.hima.list.ListItem;
import com.jermyn.hima.utils.Constants;
import com.jermyn.hima.utils.TinyTools;
import com.microsoft.fluentui.listitem.ListItemView;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * 专辑详情适配器
 * <p>
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/26
 */
public class AlbumDetailAdapter extends BaseQuickAdapter<Track, ListItemViewHolder> implements LoadMoreModule {

    Context _context;
    View _grandRootView;

    public AlbumDetailAdapter(View grandRootView, Context context) {
        super(0);
        _context = context;
        _grandRootView = grandRootView;
    }

    @Override
    protected void convert(@NonNull ListItemViewHolder holder, Track track) {

        TextView customAccessoryView =  new TextView(_context);
        TextViewCompat.setTextAppearance(customAccessoryView, R.style.TextAppearance_ListItemValue);
        customAccessoryView.setText(TinyTools.convertTimestamp2Date(track.getUpdatedAt(), Constants.TIME_FORMAT_DATE));

        TextView customView = new TextView(_context);
        TextViewCompat.setTextAppearance(customView, R.style.TextAppearance_ListItemValue);
        int position = ((RecyclerView.ViewHolder)((BaseViewHolder)holder)).getPosition();
        customView.setText(String.valueOf(position + 1));
        customView.setGravity(Gravity.CENTER);

        ListItem listItem = new ListItem(track.getTrackTitle());
        listItem.setFooter("播放量: " + track.getPlayCount() + "  时长: " + TinyTools.formatDuration(track.getDuration()));
        listItem.setCustomAccessoryView(customAccessoryView);
        listItem.setCustomView(customView);
        listItem.setCustomViewSize(ListItemView.CustomViewSize.LARGE);

        holder.setListItem(listItem);

    }

    @NonNull
    @Override
    protected ListItemViewHolder onCreateDefViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ListItemView view = new ListItemView(_context);
        view.setLayoutParams(lp);
        return new ListItemViewHolder(view);
    }

    @NonNull
    @Override
    public BaseLoadMoreModule addLoadMoreModule(@NonNull BaseQuickAdapter<?, ?> baseQuickAdapter) {
        return new BaseLoadMoreModule(baseQuickAdapter);
    }
}
