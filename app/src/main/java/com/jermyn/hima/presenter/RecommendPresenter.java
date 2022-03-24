package com.jermyn.hima.presenter;

import com.jermyn.hima.interfaces.IRecommendPresenter;
import com.jermyn.hima.interfaces.IRecommendViewCallBack;

public class RecommendPresenter implements IRecommendPresenter {
    private static final String TAG = "RECOMEND_PRESENTER";


    private RecommendPresenter(){}

    private volatile static RecommendPresenter _instance = null;

    /**
     * 获取单例
     * @return
     */
    public static RecommendPresenter getInstance(){
        if (_instance == null) {
            synchronized (RecommendPresenter.class){
                if (_instance == null){
                    _instance = new RecommendPresenter();
                }
            }
        }
        return _instance;
    }

    @Override
    public void getRecommendList() {

    }

    @Override
    public void pull2Refresh() {

    }

    @Override
    public void loadMore() {

    }

    @Override
    public void registerViewCallBack(IRecommendViewCallBack callBack) {

    }

    @Override
    public void unRegisterViewCallBack(IRecommendViewCallBack callBack) {

    }
}
