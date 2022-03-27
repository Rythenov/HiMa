package com.jermyn.hima.interfaces;

import com.jermyn.hima.base.IBasePresenter;

public interface IRecommendPresenter extends IBasePresenter<IRecommendViewCallBack> {
    /**
     * 获取推荐内容
     */
    void getRecommendList();

    /**
     * 下拉刷新
     */
    void pull2Refresh();

    /**
     * 上拉加载
     */
    void loadMore();

}
