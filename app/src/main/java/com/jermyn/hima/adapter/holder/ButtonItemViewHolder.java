package com.jermyn.hima.adapter.holder;

import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jermyn.hima.list.base.IButtonItem;
import com.microsoft.fluentui.widget.Button;

/**
 * Fluent风格ButtonItemHolder
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/26
 */
public class ButtonItemViewHolder extends BaseViewHolder {
    private Button button;

    public ButtonItemViewHolder(FrameLayout view){
        super(view);
        View childView = view.getChildAt(0);
        if (childView != null && childView instanceof Button){
            button = (Button) childView;
        }

    }

    public void setButtonItem(IButtonItem buttonItem) {
        button.setText(buttonItem.getButtonText());
        button.setId(buttonItem.getId());
        button.setOnClickListener(buttonItem.getOnClickListener());
    }
}
