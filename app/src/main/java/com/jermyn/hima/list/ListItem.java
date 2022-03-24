package com.jermyn.hima.list;

import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.jermyn.hima.list.base.IListItem;
import com.microsoft.fluentui.listitem.ListItemView;

public class ListItem extends IListItem {

    public ListItem(String title) {
        this.title = title;
        this.subtitle = "";
        this.footer = "";

        this.titleMaxLines = ListItemView.DEFAULT_MAX_LINES;
        this.subtitleMaxLines = ListItemView.DEFAULT_MAX_LINES;
        this.footerMaxLines = ListItemView.DEFAULT_MAX_LINES;

        this.titleTruncateAt = ListItemView.Companion.getDEFAULT_TRUNCATION();
        this.subtitleTruncateAt = ListItemView.Companion.getDEFAULT_TRUNCATION();
        this.footerTruncateAt = ListItemView.Companion.getDEFAULT_TRUNCATION();

        this.customView = null;
        this.customViewSize = ListItemView.Companion.getDEFAULT_CUSTOM_VIEW_SIZE();
        this.customAccessoryView = null;
        this.customSecondarySubtitleView = null;

        this.layoutDensity = ListItemView.Companion.getDEFAULT_LAYOUT_DENSITY();
    }

    public static ListItem createListItem(String title//: String
            , String subtitle//: String = ""
            , String footer//: String = ""
            , View customView//: View? = null
            , ListItemView.CustomViewSize customViewSize//:  = DEFAULT_CUSTOM_VIEW_SIZE
            , View customAccessoryView//: View? = null
            , View customSecondarySubtitleView//: View? = null
            , Boolean addCustomAccessoryViewClick//: Boolean = false
            , View.OnClickListener listener
            , ListItemView.LayoutDensity layoutDensity//:  = DEFAULT_LAYOUT_DENSITY
            , Boolean wrap//: Boolean = false
    ) {
        ListItem listItem = new ListItem(title);
        listItem.title = title;
        listItem.subtitle = subtitle;
        listItem.footer = footer;
        listItem.layoutDensity = layoutDensity;
        listItem.customAccessoryView = customAccessoryView;
        listItem.customView = customView;
        listItem.customViewSize = customViewSize;
        listItem.customSecondarySubtitleView = customSecondarySubtitleView;

        if (wrap) {
            listItem.titleMaxLines = 4;
            listItem.subtitleMaxLines = 4;
            listItem.footerMaxLines = 4;
        } else {
            listItem.titleTruncateAt = TextUtils.TruncateAt.MIDDLE;
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                // In earlier APIs this crashes with an ArrayIndexOutOfBoundsException
                listItem.subtitleTruncateAt = TextUtils.TruncateAt.START;
            }
        }

        if (addCustomAccessoryViewClick && customAccessoryView != null) {
            customAccessoryView.setOnClickListener(listener);
        }
        return listItem;
    }


}
