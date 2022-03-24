package com.jermyn.hima.interfaces;

public interface IRecommendPresenter {
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

    /**
     * 注册UI的回调
     * @param callBack
     */
    void registerViewCallBack(IRecommendViewCallBack callBack);

    /**
     * 取消UI的回调
     * @param callBack
     */
    void unRegisterViewCallBack(IRecommendViewCallBack callBack);


}
