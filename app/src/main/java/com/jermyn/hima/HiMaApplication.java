package com.jermyn.hima;

import android.app.Application;
import android.content.Context;

import com.ximalaya.ting.android.opensdk.datatrasfer.CommonRequest;
import com.ximalaya.ting.android.opensdk.datatrasfer.DeviceInfoProviderDefault;

public class HiMaApplication extends Application {

    private static final String APP_KEY  = "26a14a3d3e401bf4131509a477007aa7";
    private static final String PACK_ID  = "com.jermyn.hima";
    private static final String APP_SECRET  = "1f5b7e283cd1088613645c8fd96ffb11";

    private static Context mContext;

    private static HiMaApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mContext = getApplicationContext();
        CommonRequest mXimalaya = CommonRequest.getInstanse();
        mXimalaya.setAppkey(APP_KEY);
        mXimalaya.setPackid(PACK_ID);
        // 优先取oaid作为设备ID，如果获取不到再按照列表顺序优先级进行获取，如果出于用户隐私数据安全考虑，可以对得到的设备ID再进行MD5/SHA1/SHA256哈希，注意不要加盐，并请告知平台技术支持同学。
        mXimalaya.init(this ,APP_SECRET, new DeviceInfoProviderDefault(mContext) {
            @Override
            public String oaid() {
                // 合作方要尽量优先回传用户真实的oaid，使用oaid可以关联并打通喜马拉雅主app中记录的用户画像数据，对后续个性化推荐接口推荐给用户内容的准确性会有极大的提升！
                // demo已实现oaid的接入可以参考下
                return "!!!这里要传入真正的oaid oaid 接入请访问 http://www.msa-alliance.cn/col.jsp?id=120";
            }
        });
    }

    public static Context getContext() {
        return mContext;
    }

    public static HiMaApplication getInstance() {
        return app;
    }
}
