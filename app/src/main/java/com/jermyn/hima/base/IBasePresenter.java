package com.jermyn.hima.base;

import com.jermyn.hima.interfaces.IRecommendViewCallBack;

/**
 * 🙏🏻 GOD BLESS MY CODE ！
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/27
 */
public interface IBasePresenter<T> {
    /**
     * 注册UI的回调
     * @param callBack
     */
    void registerViewCallBack(T callBack);

    /**
     * 取消UI的回调
     * @param callBack
     */
    void unRegisterViewCallBack(T callBack);

}
