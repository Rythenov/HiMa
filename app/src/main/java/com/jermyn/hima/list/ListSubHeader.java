package com.jermyn.hima.list;

import android.view.View;

import com.jermyn.hima.list.base.IListSubHeader;
import com.microsoft.fluentui.listitem.ListSubHeaderView;

public class ListSubHeader extends IListSubHeader {
    public ListSubHeader(){
        title = "";
        ListSubHeaderView.TitleColor titleColor = ListSubHeaderView.Companion.getDEFAULT_TITLE_COLOR();
        View customAccessoryView = null;
    }
    public ListSubHeader(String title){
        this.title = title;
        ListSubHeaderView.TitleColor titleColor = ListSubHeaderView.Companion.getDEFAULT_TITLE_COLOR();
        View customAccessoryView = null;
    }
}
