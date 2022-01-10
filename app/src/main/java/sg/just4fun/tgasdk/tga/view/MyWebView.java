package sg.just4fun.tgasdk.tga.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class MyWebView extends WebView {
    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    };


    @SuppressLint("SetJavaScriptEnabled")
    public MyWebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSetting(arg0,this);
    }
    @SuppressLint("SetJavaScriptEnabled")
    public MyWebView(Context arg0) {
        super(arg0);
        this.setWebViewClient(client);
        // this.setWebChromeClient(chromeClient);
        // WebStorage webStorage = WebStorage.getInstance();
        initWebViewSetting(arg0,this);
    }

    public static void initWebViewSetting(Context context, WebView webView) {
        if (webView == null) {
            return;
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //允许混合内容 解决部分手机 加载不出https请求里面的http下的图片
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //设置自适应屏幕，两者合用
        webSettings.setJavaScriptEnabled(true);
        //启用数据库
        webSettings.setDatabaseEnabled(true);
        String dbPath = context.getApplicationContext().getDir("db",Context.MODE_PRIVATE).getPath();
        webSettings.setDatabasePath(dbPath);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //设置定位的数据库路径
//        String dir = context.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
//        webSettings.setGeolocationDatabasePath(dir);
        //启用地理定位
//        webSettings.setGeolocationEnabled(true);
        //开启DomStorage缓存
        webSettings.setDomStorageEnabled(true);

        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSupportZoom(false); // 支持缩放
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        String ua = webSettings.getUserAgentString();


//        webSettings.setBlockNetworkImage(true);
    }

    // Log.d(
}
