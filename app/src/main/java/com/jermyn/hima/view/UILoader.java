package com.jermyn.hima.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.jermyn.hima.R;
import com.jermyn.hima.base.HiMaApplication;

/**
 * UI加载器
 * <p>
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/25
 */
public abstract class UILoader extends LinearLayout {

    public enum UIStatus {
        LOADING, SUCCESS, NETWORK_ERROR, EMPTY, NONE
    }

    private UIStatus _status = UIStatus.NONE;

    private View _loadingView = null;
    private View _successView = null;
    private View _networkErrorView = null;
    private View _emptyView = null;

    public UILoader(Context context) {
        super(context);
    }

    public UILoader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public UILoader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化UI
     */
    private void initView() {
        switchUIByCurrentStatus();
    }

    private void switchUIByCurrentStatus() {
        if (_loadingView == null) {
            _loadingView = getLoadingView();
            addView(_loadingView);
        }
        _loadingView.setVisibility(_status == UIStatus.LOADING ? VISIBLE : GONE);

        if (_successView == null) {
            _successView = getSuccessView(this);
            addView(_successView);
        }
        _successView.setVisibility(_status == UIStatus.SUCCESS ? VISIBLE : GONE);

        if (_networkErrorView == null) {
            _networkErrorView = getNetworkErrorView();
            addView(_networkErrorView);
        }
        _networkErrorView.setVisibility(_status == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);

        if (_emptyView == null) {
            _emptyView = getEmptyView();
            addView(_emptyView);
        }
        _emptyView.setVisibility(_status == UIStatus.NETWORK_ERROR ? VISIBLE : GONE);
    }

    public void updateUI(UIStatus status){
        _status = status;
        //更新UI一定要在主线程
        HiMaApplication.getHandler().post(() -> {
            switchUIByCurrentStatus();
        });
    }

    protected View getEmptyView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_empty_view, this, false);
    }

    protected View getNetworkErrorView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_network_error_view, this, false);
    }

    protected abstract View getSuccessView(ViewGroup container);

    protected View getLoadingView(){
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_view, this, false);
    }

    public UIStatus getStatus() {
        return _status;
    }

    public void setStatus(UIStatus status) {
        this._status = status;
    }
}
