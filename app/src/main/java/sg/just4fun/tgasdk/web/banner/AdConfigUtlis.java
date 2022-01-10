package sg.just4fun.tgasdk.web.banner;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import sg.just4fun.tgasdk.modle.AdConfigBean;
import sg.just4fun.tgasdk.modle.UserInFoBean;
import sg.just4fun.tgasdk.web.TgaSdk;

public class AdConfigUtlis {

    private static List<AdConfigBean> appConfigbeanList;
    private static String adConfig;

    public static void getBannerConfigEvents(WebView webView, String uuid) {
        try {
//            String appConfig="{\"ad\":[{\"moduleAppId\": \"1\",\"channelName\": \"applovin\",\"weight\": 0,\"enabled\": true,\"config\": {\"titleAd\": \"496b75871f5eddb5 \",\"rewardAd\": \"627b6b1671ed3ae4 \",\"bannerAd\": \"a90639a2214ceca8\"}},{\"moduleAppId\": \"2\",\"channelName\": \"googleAfg\",\"weight\": 1,\"enabled\": true,\"config\":null}],\"share\": {\"allEnabled\": true,\"channels\": [{\"code\": \"app\",\"title\": \"Share to friend\",\"enabled\": true},{\"code\": \"facebook\",\"title\": \"Facebook\",\"enabled\": true}]},\"payMentList\": [{\"moduleAppId\": \"3\",\"channelName\": \"googlePay\",\"enabled\": true}],\"gameCentreUrl\": \"https://data.just4fun.sg/h5tga/\"}";
//            Gson gson = new Gson();
//            UserInFoBean1.AppConfig appConfig1 = gson.fromJson(appConfig, UserInFoBean1.AppConfig.class);
//            UserInFoBean1.AppConfigBean appConfigBean = gson.fromJson(ss, UserInFoBean1.AppConfigBean.class);
//            TgaSdkEventDataBean tgaSdkEventDataBean = new TgaSdkEventDataBean(uuid, appConfig1);
            String appName = getAppName(TgaSdk.mContext);
            if (TgaSdk.appConfigList!=null&&!TgaSdk.appConfigList.equals("")){
                JSONObject jsonObject1=new JSONObject(TgaSdk.appConfigList);
                jsonObject1.put("packageName",TgaSdk.packageName);
                jsonObject1.put("appName",appName);
                jsonObject1.put("icon",TgaSdk.iconpath);
                jsonObject1.put("appId",TgaSdk.appId);
                jsonObject1.put("schemeUrl",TgaSdk.schemeUrl);
                String s1 = jsonObject1.toString();
                Log.e("配置表回调","s1="+s1);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("uuid",uuid);
                jsonObject.put("data",s1);
                String s = jsonObject.toString();
                String scriptCode = toJavaScriptCode("null", s);
                Log.e("配置表回调","toJavaScriptCode="+scriptCode);
                webView.post(new ScriptCodeRunnable(scriptCode, webView));
            }else {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("uuid",uuid);
                jsonObject.put("data","{}");
                String s = jsonObject.toString();
                String scriptCode = toJavaScriptCode("null", s);
                webView.post(new ScriptCodeRunnable(scriptCode, webView));
            }
        } catch (Exception e) {
            Log.e("拿到值","是否拿到Config="+e.getMessage());
            e.printStackTrace();
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("uuid",uuid);
                jsonObject.put("data","{}");
                String s = jsonObject.toString();
                String scriptCode = toJavaScriptCode("null", s);
                webView.post(new ScriptCodeRunnable(scriptCode, webView));
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }

        }
    }
    public static String toJavaScriptCode(String error,String string )  {
        return "TgaSdk.onEvent("+error+", "+string+")";
    }

    public static class ScriptCodeRunnable implements Runnable {
        private final String scriptCode;
        private final WebView webView;
        public ScriptCodeRunnable(String scriptCode, WebView webView) {
            this.scriptCode = scriptCode;
            this.webView = webView;
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void run() {
            Log.e("那配置表  执行了回调", "JSON="+scriptCode +" run on " +
                    webView.getUrl());
            webView.evaluateJavascript(scriptCode, null);
        }
    }
    /***
     * 获取应用程序名称。
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
