package sg.just4fun.tgasdk.web.login;

import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

import sg.just4fun.tgasdk.adsdk.TgaSdkEventDataBean;
import sg.just4fun.tgasdk.modle.BipGameUserInfo;

public class LoginUtils {

    public static String toJavaScriptCode(String error,String string )  {
        return "TgaSdk.onEvent("+error+", "+string+")";
    }

    public static void codeEvents(WebView webView, String uuid, boolean success, String accessToken,String refreshToken) {

        LoginCallblackInfo codeInfo = new LoginCallblackInfo(success,accessToken,refreshToken);
        TgaSdkEventDataBean tgaSdkEventDataBean = new TgaSdkEventDataBean(uuid, codeInfo);
        try {
            String scriptCode = toJavaScriptCode("null", tgaSdkEventDataBean.toJsCode());
            Log.e("回调","share="+scriptCode+"  run on="+webView);
            webView.post(new ScriptCodeRunnable(scriptCode, webView));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void codeEvents(WebView webView, String uuid, boolean success) {
        LoginCallblackInfo codeInfo = new LoginCallblackInfo(success);
        TgaSdkEventDataBean tgaSdkEventDataBean = new TgaSdkEventDataBean(uuid, codeInfo);
        try {
            String scriptCode = toJavaScriptCode("null", tgaSdkEventDataBean.toJsCode());
            Log.e("回调","share="+scriptCode+"  run on="+webView);
            webView.post(new ScriptCodeRunnable(scriptCode, webView));
        } catch (Exception e) {
            e.printStackTrace();
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
            Log.d("执行了回调", "JSON="+scriptCode +" run on " +
                    webView.getUrl());
            webView.evaluateJavascript(scriptCode, null);
        }
    }

}
