package com.jermyn.hima.base;

import com.jermyn.hima.interfaces.IRecommendViewCallBack;

/**
 * ðð» GOD BLESS MY CODE ï¼
 *
 * @author CHCNAV BDZH Jermyn on 2022/3/27
 */
public interface IBasePresenter<T> {
    /**
     * æ³¨åUIçåè°
     * @param callBack
     */
    void registerViewCallBack(T callBack);

    /**
     * åæ¶UIçåè°
     * @param callBack
     */
    void unRegisterViewCallBack(T callBack);

}
