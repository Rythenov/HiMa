package com.jermyn.hima.adapter.holder;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jermyn.hima.list.base.IListSubHeader;
import com.microsoft.fluentui.listitem.ListSubHeaderView;

/**
 * Fluent风格ListSubHeaderHolder
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/26
 */
public class ListSubHeaderViewHolder extends BaseViewHolder {
    private ListSubHeaderView listSubHeaderView;

    public ListSubHeaderViewHolder(ListSubHeaderView view){
        super(view);
        listSubHeaderView = view;
    }

    public void setListSubHeader(IListSubHeader listSubHeader) {
        listSubHeaderView.setTitle(listSubHeader.getTitle());
        listSubHeaderView.setTitleColor(listSubHeader.getTitleColor());
        listSubHeaderView.setCustomAccessoryView(listSubHeader.getCustomAccessoryView());
    }
}
