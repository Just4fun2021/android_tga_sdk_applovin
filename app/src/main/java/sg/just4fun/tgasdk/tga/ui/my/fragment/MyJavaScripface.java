package sg.just4fun.tgasdk.tga.ui.my.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import sg.just4fun.tgasdk.R;
import sg.just4fun.tgasdk.adsdk.TgaAdSdkUtils;
import sg.just4fun.tgasdk.adsdk.TgaApiBean;
import sg.just4fun.tgasdk.adsdk.applovin.ApplovinApiBean;
import sg.just4fun.tgasdk.modle.EncryptStrBean;
import sg.just4fun.tgasdk.modle.GooglePayInfo;
import sg.just4fun.tgasdk.modle.GooglePayInfoBean;
import sg.just4fun.tgasdk.modle.PriceBean;
import sg.just4fun.tgasdk.tga.base.HttpBaseResult;
import sg.just4fun.tgasdk.tga.base.JsonCallback;
import sg.just4fun.tgasdk.tga.global.AppUrl;
import sg.just4fun.tgasdk.tga.ui.home.model.TgaSdkUserInFo;
import sg.just4fun.tgasdk.tga.utils.SpUtils;
import sg.just4fun.tgasdk.tga.utils.ToastUtil;
import sg.just4fun.tgasdk.web.Conctart;
import sg.just4fun.tgasdk.web.TgaSdk;
import sg.just4fun.tgasdk.web.banner.AdConfigUtlis;
import sg.just4fun.tgasdk.web.banner.AppLovinAdPlacementConfig;
import sg.just4fun.tgasdk.web.goPage.GoPageUtils;

public class MyJavaScripface {
    // The Activity that load the webview with this interface instance.
    Activity context;
    private String TGA="JavaScriptinterface";
    String tgaUrl;
    //    TTAdNative ttAdNative = null;
    private final String FB_PLACEMENT_ID= "503651173800345_658237148341746";
    private WebView webview;
    private String payUuid;
    private String orderId;
    private String price;
    private static List<GooglePayInfo> infoList=new ArrayList<>();
    private static String ggOrder;
    private int isscu=0;
    private int cishu=5;
    private String metaDataStringApplication1;

    //    public static WebView webView;
    public MyJavaScripface(Activity context, String tgaUrl){
        this.context= context;
//      this.webview = webview;
        this.tgaUrl = tgaUrl;

    }
    private int pageId;

    public void registerWebview(@NonNull WebView webview) {
        webview.addJavascriptInterface(this, "TgaAndroid");
        this.webview = webview;
        TgaAdSdkUtils.registerTgaWebview(webview);
    }

    TgaApiBean vungle;
    TgaApiBean apploving;

    public void init(@NonNull WebView webview) {
//        try{
//            vungle = new VungleApiBean(context, webview, tgaUrl);
//            vungle.initSdk();
//        } catch(Exception e){
//
//        }

        if (TgaSdk.appConfigbeanList==null){
            TgaSdk.getUserInfo(TgaSdk.appPaymentKey,context);
        }

        metaDataStringApplication1 = Conctart.getMetaDataStringApplication(context,"applovin.sdk.key", "");
        Log.d("tgasdk-js", "Apploving Init Begin");
        Log.d(TGA, "apploving="+metaDataStringApplication1);

        if (!metaDataStringApplication1.equals("")){
            AppLovinAdPlacementConfig appLovinAdPlacementConfig = new AppLovinAdPlacementConfig();
            try {
                if(TgaSdk.applovnIdConfig!=null){
                    JSONObject jsonObject = new JSONObject(TgaSdk.applovnIdConfig);
                    appLovinAdPlacementConfig.fromJson(jsonObject);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try{
                if(TgaSdk.applovnIdConfig!=null){
                    apploving = new ApplovinApiBean(context, webview, tgaUrl);
                    apploving.initSdk();
                }
            } catch(Exception e) {
                Log.e("tgasdk-js", "Apploving Init Error", e);

            }
        }

        registerWebview(webview);
    }



//    /**
//     * 与js交互时用到的方法，在js里直接调用的
//     */
//    @JavascriptInterface
//    public void getComming() {
//        Intent intent = new Intent(context,CommingActivity.class);
//        context.startActivity(intent);
//    }

    @JavascriptInterface
    public String getTgaSdkVersion(String type){
        return "0.1";
    }


    @JavascriptInterface
    public void facebookeLogin() {
        Log.d("FB","facebookeLogin");
    }
//
//    @JavascriptInterface
//    public void quitLogin(String uuid,Object s) {
//        Log.d("是不是","quitLogin");
//        if(TGASDK.listener != null) {
//            TGASDK.listener.quitLogin();
//        }
//        SpUtils.clean(TGASDK.mContext);
//        ActivityDele.finishAllActivity();
//        context.finish();
//    }


    @JavascriptInterface
    public void logout(String uuid,String options) {

        Log.d("是不是","logout");
        if(TgaSdk.listener != null) {
            TgaSdk.listener.quitLogin(context);
        }
        SpUtils.clean(TgaSdk.mContext);
        context.finish();
    }



    @JavascriptInterface
    public void appPayment(String orderId, String productId, String callback, String type) {
        Log.d( "INAPP PAY",  "orderId=" + orderId + ",productId=" + productId + "type=" + type);
    }


    @JavascriptInterface
    public void showVungleAd(String uuid, String adType) {
        vungle.showAd(uuid, adType);
    }

    @JavascriptInterface
    public void showApplovinAd(String uuid, String adType) {
        Log.d(apploving.getTag(), "showApplovingAd(" + uuid + "," + adType + ")");
        Log.d("eZx4Pox", "showApplovingAd(" + uuid + "," + adType + ")");
        if(!metaDataStringApplication1.equals("")){
            if (TgaSdk.applovnIdConfig!=null){
                apploving.showAd(uuid, adType);
            }
        }

    }
    //    切换横屏
    @JavascriptInterface
    public void HorizontalScreen(String uuid, String options) {
        Log.e("HorizontalScreen","横屏options="+options);
        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    //    切换竖屏
    @JavascriptInterface
    public void VerticalScreen(String uuid, String options) {
        Log.e("VerticalScreen","竖屏options="+options);
        context.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //切换竖屏
    }


    @JavascriptInterface
    public void showApplovinBannerAd(String uuid, String params) {
        Log.d(apploving.getTag(), "showApplovinBannerAd(" + uuid + "," + params + ")");
//        Log.d("eZx4Pox", "showApplovinBannerAd(" + uuid + "," + params + ")");
//        apploving.showBannerAd(uuid, "banner", params);
        if(!metaDataStringApplication1.equals("")){
            if (TgaSdk.applovnIdConfig!=null){
                apploving.showAd(uuid, "banner");
            }
        }

    }

    @JavascriptInterface
    public void hideApplovingBannerAd(String uuid, String options) {
        Log.e("hideApplovingBannerAd","广告"+options);
        if(!metaDataStringApplication1.equals("")){
            if (TgaSdk.applovnIdConfig!=null){
                apploving.hideBannerAd(uuid,"banner");
            }
        }


    }

    @JavascriptInterface
    public void showVungleBannerAd(String uuid, String params) {
        vungle.showBannerAd(uuid, "banner", params);
    }

    @JavascriptInterface
    public void hideVungleBannerAd(String uuid, String options) {
        vungle.hideBannerAd(uuid,"");

    }
    @JavascriptInterface
    public void openBrowser(String uuid, String options) {
        Uri uri = Uri.parse(options);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @JavascriptInterface
    public void goPage(String uuid,  String options) {
        Log.e("goPage","goPage");
        pageId++;
        GoPageUtils.jumpGame(pageId,context,uuid,options);
    }

    @JavascriptInterface
    public void finishPage(String uuid,  String options) {
        context.finish(); //返回键点击
    }
    @JavascriptInterface
    public void getPayAppid(String uuid,  String options) {
        Log.e("googlePayWay","getPayAppid"+options);
    }
    //sdk支付
    @JavascriptInterface
    public void sdkPay(String uuid,  String options) {
    }
    //    app自身支付
    @JavascriptInterface
    public void appPay(String uuid,  String options) {
        Log.e("appPay","options"+options);
    }


    @JavascriptInterface
    public void inAppShare(String uuid,  String options) {

    }



    @JavascriptInterface
    public void thirdPartyShare(String uuid,  String options) {

    }
    //获取广告配置表
    @JavascriptInterface
    public void getAppConfig(String uuid, String options) {
        Log.e("获取广告配置表","getBannerConfig");
        AdConfigUtlis.getBannerConfigEvents(webview,uuid);
    }

    //    @JavascriptInterface
//    public void showFacebookAd(String uuid, String adType) {
//        facebook.showAd(uuid, adType);
//    }
    @JavascriptInterface
    public void goMyFragment(String uuid, String options) {
        TgaSdk.listener.goMyFragemnt(context);
    }


    @JavascriptInterface
    public String checkEvent(String uuid,String options) {
        return TgaAdSdkUtils.checkEvent(uuid);
    }

    @JavascriptInterface
    public void releaseEvent(String uuid,String options) {
        TgaAdSdkUtils.releaseEvent(uuid);
    }

    @JavascriptInterface
    public void releaseAllEvents(String uuid,String options) {
        TgaAdSdkUtils.clearCaches();
    }


    public void test() {
        this.apploving.showAd(UUID.randomUUID().toString().replace("-",""), "fullscreen");
    }

    @JavascriptInterface
    public void tryFlushEvents(String uuid, String options) {
        Log.d("javascriptInterface", "tryFlushEvents " + uuid + " " + options);
        TgaAdSdkUtils.flushAllEvents(webview);
    }

//        public void onEvent(String event, JSONObject params) {
//        webview.evaluateJavascript("tgasdk.nativeEvent(\"" +event + "\", " + params.toString(), null);
//    }






    public static String getNumber(String str){
        // 控制正则表达式的匹配行为的参数(小数)
        Pattern p = Pattern.compile("(\\d+\\.\\d+)");
        //Matcher类的构造方法也是私有的,不能随意创建,只能通过Pattern.matcher(CharSequence input)方法得到该类的实例.
        Matcher m = p.matcher(str);
        //m.find用来判断该字符串中是否含有与"(\\d+\\.\\d+)"相匹配的子串
        if (m.find()) {
            //如果有相匹配的,则判断是否为null操作
            //group()中的参数：0表示匹配整个正则，1表示匹配第一个括号的正则,2表示匹配第二个正则,在这只有一个括号,即1和0是一样的
            str = m.group(1) == null ? "" : m.group(1);
        } else {
            //如果匹配不到小数，就进行整数匹配
            p = Pattern.compile("(\\d+)");
            m = p.matcher(str);
            if (m.find()) {
                //如果有整数相匹配
                str = m.group(1) == null ? "" : m.group(1);
            } else {
                //如果没有小数和整数相匹配,即字符串中没有整数和小数，就设为空
                str = "";
            }
        }
        return str;
    }

}
