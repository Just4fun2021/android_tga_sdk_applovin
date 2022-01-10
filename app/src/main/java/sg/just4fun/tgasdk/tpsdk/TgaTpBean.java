package sg.just4fun.tgasdk.tpsdk;

import android.app.Activity;
import android.webkit.WebView;



/**
 * 广告以外的第三方平台apiBean集合。当前版本主要是分享功能
 */
public interface TgaTpBean {
    void initSdk();
    boolean isInitiated();
    String getTag();

}
