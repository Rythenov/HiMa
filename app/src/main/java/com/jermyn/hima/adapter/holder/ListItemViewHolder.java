package com.jermyn.hima.adapter.holder;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jermyn.hima.list.base.IListItem;
import com.microsoft.fluentui.listitem.ListItemView;

/**
 * Fluent风格ListItemHolder
 * <p>
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/26
 */
public class ListItemViewHolder extends BaseViewHolder{

    private ListItemView listItemView;

    public ListItemViewHolder(ListItemView view) {
        super(view);
        listItemView = view;
    }

    public void setListItem(IListItem listItem) {
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

    public void clearCustomViews() {
        listItemView.setCustomView(null);
        listItemView.setCustomAccessoryView(null);
    }

}
