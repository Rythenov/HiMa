package com.jermyn.hima.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.TextViewCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jermyn.hima.R;
import com.jermyn.hima.list.ListItem;
import com.jermyn.hima.list.base.IListItem;
import com.microsoft.fluentui.listitem.ListItemView;
import com.microsoft.fluentui.persona.AvatarSize;
import com.microsoft.fluentui.persona.AvatarView;
import com.microsoft.fluentui.snackbar.Snackbar;
import com.ximalaya.ting.android.opensdk.model.album.Album;

public class RecommendAdapter extends BaseQuickAdapter<Album, RecommendAdapter.ListItemViewHolder> {

    Context _context;
    View _grandRootView;

    public RecommendAdapter(View grandRootView, Context context) {
        super(0);
        _context = context;
        _grandRootView = grandRootView;
    }

    @Override
    protected void convert(@NonNull ListItemViewHolder holder, Album s) {

        AvatarView avatarView = createExampleAvatarView(_context.getDrawable(R.drawable.avatar_carole_poland)
                , s.getAlbumTitle()
                , AvatarSize.XXLARGE
                , _context);

        View accessoryView = createTextView("text", _context);
        accessoryView = createImageView(ContextCompat.getDrawable(_context, R.drawable.ic_more_vertical_24_filled), "imageView", _context);

        ListItem listItem = ListItem.createListItem(s.getAlbumTitle()
                , s.getAlbumIntro()
                , "热度:" + s.getPlayCount() + " " + "喜欢数:" + s.getFavoriteCount()
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

    private AvatarView createExampleAvatarView(Drawable avatarImageDrawable
            , String avatarNameString
            , AvatarSize avatarSize
            , Context context){
        AvatarView avatarView = new AvatarView(context);
        avatarView.setAvatarImageDrawable(avatarImageDrawable);
        avatarView.setAvatarSize(avatarSize);
        if (avatarNameString != null) {
            avatarView.setName(avatarNameString);
        }
        return avatarView;
    }


    protected class ListItemViewHolder extends BaseViewHolder {
        private ListItemView listItemView;

        ListItemViewHolder(ListItemView view){
            super(view);
            listItemView = view;
        }

        void setListItem(IListItem listItem) {
            listItemView.setTitle(listItem.getTitle());
            listItemView.setSubtitle(listItem.getSubTitle());
            listItemView.setFooter(listItem.getFooter());

            listItemView.setTitleMaxLines(listItem.getTitleMaxLines());
            listItemView.setSubtitleMaxLines(listItem.getSubtitleMaxLines());
            listItemView.setFooterMaxLines(listItem.getFooterMaxLines());

            listItemView.setTitleTruncateAt(listItem.getTitleTruncateAt());
            listItemView.setSubtitleTruncateAt(listItem.getSubtitleTruncateAt());
            listItemView.setFooterTruncateAt(listItem.getFooterTruncateAt());

            listItemView.setCustomView(listItem.getCustomView());
            listItemView.setCustomViewSize(listItem.getCustomViewSize());
            listItemView.setCustomAccessoryView(listItem.getCustomAccessoryView());
            listItemView.setCustomSecondarySubtitleView(listItem.getCustomSecondarySubtitleView());
            listItemView.setLayoutDensity(listItem.getLayoutDensity());
        }

        void clearCustomViews() {
            listItemView.setCustomView(null);
            listItemView.setCustomAccessoryView(null);
        }
    }
}
