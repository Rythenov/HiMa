package com.jermyn.hima.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jermyn.hima.R;
import com.jermyn.hima.adapter.holder.ListItemViewHolder;
import com.jermyn.hima.list.ListItem;
import com.microsoft.fluentui.listitem.ListItemView;
import com.microsoft.fluentui.persona.AvatarSize;
import com.microsoft.fluentui.persona.AvatarStyle;
import com.microsoft.fluentui.persona.AvatarView;
import com.microsoft.fluentui.snackbar.Snackbar;
import com.ximalaya.ting.android.opensdk.model.album.Album;

public class RecommendAdapter extends BaseQuickAdapter<Album, ListItemViewHolder> {

    Context _context;
    View _grandRootView;

    public RecommendAdapter(View grandRootView, Context context) {
        super(0);
        _context = context;
        _grandRootView = grandRootView;
    }

    @Override
    protected void convert(@NonNull ListItemViewHolder holder, Album s) {

        String coverUrl = s.getCoverUrlLarge();

        AvatarView avatarView = new AvatarView(_context);
        avatarView.setName(s.getAlbumTitle());
        avatarView.setAvatarSize(AvatarSize.XXLARGE);
        avatarView.setAvatarStyle(AvatarStyle.SQUARE);
        Glide.with(_context).load(coverUrl).into(new CustomTarget<Drawable>(100, 100) {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                avatarView.setImageDrawable(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                avatarView.setImageDrawable(_context.getDrawable(R.drawable.avatar_allan_munger));
            }
        });

        View accessoryView = createTextView("text", _context);
        accessoryView = createImageView(ContextCompat.getDrawable(_context, R.drawable.ic_more_vertical_24_filled), "imageView", _context);

        ListItem listItem = ListItem.createListItem(s.getAlbumTitle()
                , s.getAlbumIntro()
                , "热度:" + s.getPlayCount() + " " + "声音数:" + s.getIncludeTrackCount()
                , avatarView
                , ListItemView.CustomViewSize.LARGE
                , accessoryView
                , null
                , true
                , v -> {
                    Snackbar.Companion.make(_grandRootView, "You clicked one accessory view", Snackbar.LENGTH_SHORT, Snackbar.Style.REGULAR).show();
                }
                , ListItemView.Companion.getDEFAULT_LAYOUT_DENSITY()
                , false);
        listItem.setTitleTruncateAt(TextUtils.TruncateAt.END);
        listItem.setSubtitleTruncateAt(TextUtils.TruncateAt.END);
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

    private TextView createTextView(String text, Context context){
        TextView textView =  new TextView(context);
        TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_ListItemValue);
        textView.setText(text);
        return textView;
    }

    private ImageView createImageView(Drawable drawable, String description, Context context){
        ImageView imageView = new ImageView(context);
        imageView.setImageDrawable(drawable);
        imageView.setContentDescription(description);
        return imageView;
    }
}
