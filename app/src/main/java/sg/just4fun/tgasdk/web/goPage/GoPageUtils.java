package sg.just4fun.tgasdk.web.goPage;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import sg.just4fun.tgasdk.conctart.Conctant;
import sg.just4fun.tgasdk.tga.ui.home.model.TgaSdkUserInFo;
import sg.just4fun.tgasdk.web.Conctart;
import sg.just4fun.tgasdk.web.TgaSdk;
import sg.just4fun.tgasdk.web.WebViewGameActivity;


public class GoPageUtils {
    private static String uuid;
    private static WebView tgawebView;
    public static Map<Integer, GoPageInfo> caches = new ConcurrentHashMap<>();
    private static Activity context;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void  jumpGame(int page,WebView tgawebView,Activity context, String uuid, String options) {
        Log.e("finishActivityEvents","jumpGamepage="+page);
        GoPageUtils.tgawebView=tgawebView;
        GoPageUtils.uuid=uuid;
        GoPageUtils.context=context;
        Gson gson = new Gson();
        boolean badJson = Conctant.isBadJson(options);
        if (badJson){
            StatusaAndNavigationModel statusaAndNavigationModel = gson.fromJson(options, StatusaAndNavigationModel.class);
            StatusaAndNavigationModel.NavigationBarModel navigationBar = statusaAndNavigationModel.getNavigationBar();
            StatusaAndNavigationModel.NavigationBarModel statusBarDisplay = statusaAndNavigationModel.getStatusBarDisplay();
            Log.e("链接","链接="+statusaAndNavigationModel.getUrl());
            Intent intent = new Intent(context, WebViewGameActivity.class);
            intent.putExtra("url",statusaAndNavigationModel.getUrl());
            intent.putExtra("uuid",uuid);
            intent.putExtra("gopag",1);
            intent.putExtra("statusaBar",statusBarDisplay.isDisplay());
            intent.putExtra("statusaBarColor",statusBarDisplay.getBackgroundColor());
            intent.putExtra("navigationBar",navigationBar.isDisplay());
            intent.putExtra("backgroundColor",navigationBar.getBackgroundColor());
            intent.putExtra("yssdk",1);
            context.startActivity(intent);
        }else {
            Intent intent = new Intent(context, WebViewGameActivity.class);
            intent.putExtra("url",options);
            intent.putExtra("uuid",uuid);
            intent.putExtra("gopag",1);
            intent.putExtra("yssdk",0);
            context.startActivity(intent);
        }
            try {
                GoPageInfo goPageInfo1 = new GoPageInfo(uuid, "36");
                String s = goPageInfo1.toJson().toString();
                String cache = toJavaScriptCode("null",s);
                LinkedList<String> singleBean = new LinkedList<>();
                singleBean.add(cache);
                GoPageInfo goPageInfo = new GoPageInfo(uuid, singleBean);
                caches.put(page,goPageInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public static String toJavaScriptCode(String error,String string )  {
        return "TgaSdk.onEvent("+error+", "+string+")";
    }

    public static void finishActivityEvents(WebView webView) {
//        Log.e("finishActivityEvents","page="+page);
        for (int page : caches.keySet()) {
            Log.e("finishActivityEvents","page="+caches.keySet());
            try {
                GoPageInfo goPageInfo = caches.remove(page);
                LinkedList<String> info = goPageInfo.getInfo();
                    if (goPageInfo.getUuid().equals(uuid)){
                        for (String scriptCode : info) {
                            Log.e("执行了回调", "执行了回调"+scriptCode);
                            webView.post( new ScriptCodeRunnable(scriptCode, webView));
                        }
                    }
            } catch (Exception e) {

            }
        }
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
            Log.d("goPage执行了回调", "JSON="+scriptCode +" run on " +
                        webView.getUrl());
                webView.evaluateJavascript(scriptCode, null);
        }
    }

}
