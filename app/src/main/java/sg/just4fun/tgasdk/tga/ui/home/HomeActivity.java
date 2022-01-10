package sg.just4fun.tgasdk.tga.ui.home;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.smarx.notchlib.NotchScreenManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import sg.just4fun.tgasdk.R;
import sg.just4fun.tgasdk.adsdk.TgaAdSdkUtils;
import sg.just4fun.tgasdk.callback.TGACallback;
import sg.just4fun.tgasdk.conctart.SdkActivityDele;
import sg.just4fun.tgasdk.modle.BipGameUserInfo;
import sg.just4fun.tgasdk.tga.base.HttpBaseResult;
import sg.just4fun.tgasdk.tga.base.JsonCallback;
import sg.just4fun.tgasdk.tga.global.AppUrl;
import sg.just4fun.tgasdk.tga.utils.SpUtils;
import sg.just4fun.tgasdk.web.Conctart;
import sg.just4fun.tgasdk.web.JavaScriptinterface;
import sg.just4fun.tgasdk.web.LollipopFixedWebView;
import sg.just4fun.tgasdk.web.TgaSdk;
import sg.just4fun.tgasdk.web.goPage.GoPageUtils;
import sg.just4fun.tgasdk.web.login.LoginUtils;

public class HomeActivity extends AppCompatActivity implements TGACallback.ShareCallback , TGACallback.CodeCallback, TGACallback.OutLoginCallback{
    private static String TGA = "WebViewGameActivity";
    public static LollipopFixedWebView add_view;
    private String youxiUrl;
    private int isFrist = 0;
    private LollipopFixedWebView newWebView;
    private String lang1;
    private ImageView img_loading;
    public static RelativeLayout rl_loading;
    private int change;
    private int gopag;
    public static boolean statusaBar;
    private boolean navigationBar;
    private ImageView image_black;
    private TextView tv_webtitle;
    private RelativeLayout relayout;
    private String backgroundColor;
    public static TextView tv_stuasbar;
    private String statusaBarColor;
    private int yssdk;

    public static String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, "utf-8");
        } catch (Exception e) {
            return text;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"WrongViewCast", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_game);
        NotchScreenManager.getInstance().setDisplayInNotch(HomeActivity.this);
        SdkActivityDele.addActivity(HomeActivity.this);
        img_loading = findViewById(R.id.img_loading);
        rl_loading = findViewById(R.id.rl_loading);
        add_view = findViewById(R.id.add_view1);
        image_black = findViewById(R.id.image_black);
        tv_webtitle = findViewById(R.id.tv_webtitle);
        relayout = findViewById(R.id.relayout);
        tv_stuasbar = findViewById(R.id.tv_stuasbar);

//      banner_web = findViewById(R.id.banner_web);
        Intent intent = getIntent();
        youxiUrl = intent.getStringExtra("url");
        Log.e("游戏url","游戏url="+ youxiUrl);
        yssdk = intent.getIntExtra("yssdk",-1);
        gopag = intent.getIntExtra("gopag", -1);
        statusaBar = intent.getBooleanExtra("statusaBar", true);
        navigationBar = intent.getBooleanExtra("navigationBar", true);
        backgroundColor = intent.getStringExtra("backgroundColor");
        statusaBarColor = intent.getStringExtra("statusaBarColor");
        int statusBarHeight = getStatusBarHeight(this);
        tv_stuasbar.setHeight(statusBarHeight);
        if (statusaBarColor != null) {
            tv_stuasbar.setBackgroundColor(Color.parseColor(statusaBarColor));
        } else {
            if (yssdk==1){
                statusaBarColor="#04a7e8";
            }else {
                statusaBarColor="#23D3BE";
            }
            tv_stuasbar.setBackgroundColor(Color.parseColor(statusaBarColor));
        }
        if (backgroundColor != null) {
            relayout.setBackgroundColor(Color.parseColor(backgroundColor));
        } else {
            backgroundColor = "#ffffff";
            relayout.setBackgroundColor(Color.parseColor(backgroundColor));

        }

//状态栏显示或者掩藏
        full(statusaBar);
        image_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (gopag == 1) {
            if (navigationBar) {
                relayout.setVisibility(View.VISIBLE);
            } else {
                relayout.setVisibility(View.GONE);
            }
            upWebview(youxiUrl,gopag, "", add_view);
        } else {
            relayout.setVisibility(View.GONE);
            int change = SpUtils.getInt(this, "change", -1);
            if (change == 1) {
                String string = SpUtils.getString(this, "changelang", "");
                upWebview(youxiUrl,gopag, string, add_view);
            } else {
                if (TgaSdk.listener != null) {
                    String lang = TgaSdk.listener.getLang();
                    if (lang == null || lang.trim().equals("")) {
                        String local = Locale.getDefault().toString();
                        lang1 = Conctart.toStdLang(local);
                        upWebview(youxiUrl,gopag, lang1, add_view);
                    } else {
                        //TODO: 需要开发标准化处理lang。这里暂时假定客户的APP给的已经是标准化的了
                        String s = Conctart.toStdLang(lang);
                        upWebview(youxiUrl,gopag, s, add_view);
                    }
                } else {
                    String local = Locale.getDefault().toString();
                    lang1 = Conctart.toStdLang(local);
                    upWebview(youxiUrl,gopag, lang1, add_view);
                }
            }
        }

    }

    /**
     * 获取statusBar的高度
     */
    public static int getStatusBarHeight(Context context) {
        return getDimensionPixel(context, "status_bar_height");
    }

    private void full(boolean enable) {
        if (enable) {//显示
            tv_stuasbar.setVisibility(View.VISIBLE);
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            transparencyBar(HomeActivity.this);
        } else {//掩藏
            tv_stuasbar.setVisibility(View.GONE);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

    }

    private static int getDimensionPixel(Context context, String navigation_bar_height) {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(navigation_bar_height, "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 修改状态栏为全透明
     *
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void upWebview(String url,int pag, String lang, WebView webView) {
        Glide.with(HomeActivity.this).load(R.mipmap.gif)
                .into(img_loading);
        rl_loading.setVisibility(View.VISIBLE);
        if (TgaSdk.listener != null) {
                            String info = TgaSdk.listener.getAuthCode();
//                            SpUtils.putString(HomeActivity.this, "userInfo", info);
//                            TgaSdkUserInFo userInFo = new TgaSdkUserInFo();
//                            try {
//                                JSONObject jsonObject = new JSONObject(info);
//                                userInFo.fromJson(jsonObject);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                            if (pag == 0) {
                                url = url + "&lang=" + lang;
                            }
                            Log.e(TGA, "lang=" + lang + "   url=" + url);
                            initWebView(webView);
                            Log.e(TGA, "地址" + url);
                            webView.loadUrl(url);
            add_view.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    Uri uri = Uri.parse(url);
                    Log.e("地址","h5页面="+url);
                    String shareParam = uri.getQueryParameter("tgashare");
                    if (!TextUtils.isEmpty(shareParam)) {
                        try {
                            shareParam = URLDecoder.decode(shareParam, "UTF-8");
                            shareParam = URLDecoder.decode(shareParam, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Uri shareUri = Uri.parse("http://www.test.com?" + shareParam);
                    /*
                    Log.d("TGA_URL", shareUri.toString());
                    Log.d("=share=", "=title==" + shareUri.getQueryParameter("title"));
                    Log.d("=share=", "=url==" + shareUri.getQueryParameter("url"));
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, shareUri.getQueryParameter("title") + " " + shareUri.getQueryParameter("url"));
                    //UserAgreementActivity.this.startActivity(Intent.createChooser(intent, ""));
                    startActivity(intent);*/
//                    shareFaceBook(shareUri.getQueryParameter("url"));
                        return true;
                    } else if (!TextUtils.isEmpty(uri.toString())) {
                        Log.d("TGA_URL", uri.toString());
                        add_view.loadUrl(uri.toString());
                        return false;
                    }
                    return false;
                }

                @Override
                public void onPageFinished(WebView webView, String url) {
                    super.onPageFinished(webView, url);
                    rl_loading.setVisibility(View.GONE);
                    Log.e("地址", "加载h5页面结束" + url + ", webview.orgurl=" + webView.getOriginalUrl() +", webview.url = " + webView.getUrl());
                }
/*@Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                alertDialog.dismiss();
            }*/

            });


//google ads
//            h5AdsWebViewClient = new H5AdsWebViewClient(this, webView);
//            webView.setWebViewClient(h5AdsWebViewClient);
//
//            WebViewClient pubWebViewClient=new WebViewClient();
//            h5AdsWebViewClient.setDelegateWebViewClient(pubWebViewClient);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {////h5谷歌调试
                add_view.setWebContentsDebuggingEnabled(true);
            }
            JavaScriptinterface tgaBridge = new JavaScriptinterface(HomeActivity.this, url);
            tgaBridge.init(add_view); //初始化所有需要BRIDGE的SDK
            TgaAdSdkUtils.registerTgaWebview(this.add_view);
        }
    }


    private void initWebView(WebView webView) {
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0).getPath());
//        webSetting.en
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            add_view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        TGACallback.setCodeCallback(this);
        TGACallback.setOutLoginCallback(this);
        isFrist++;
        TGACallback.setLangCallback(new TGACallback.LangCallback() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void getLang(String lang) {
                if (!lang.equals("")) {
                    change = 1;
                    Log.e(TGA, "语言=" + lang);
                    SpUtils.putInt(HomeActivity.this, "change", change);
                    String s = Conctart.toStdLang(lang);
                    SpUtils.putString(HomeActivity.this, "changelang", s);
                    upWebview(youxiUrl,gopag, s, add_view);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        TGACallback.setShareCallback(this);
        if (isFrist > 1) {
            if (this.add_view != null) { //每次唤醒都要重新注册当前webview到广告控件
                TgaAdSdkUtils.registerTgaWebview(this.add_view);
            }
            Log.e(TGA, "第二次isFrist=" + isFrist);
            TgaAdSdkUtils.flushAllEvents(this.add_view);
            Log.e(TGA, "第二次add_view=" + add_view.getUrl());
            GoPageUtils.finishActivityEvents(add_view);
        }
    }


    @Override
    public void shareCall(String uuid, boolean success) {
        Log.e(TGA, "webvigame=" + uuid + " 成功==" + success);
    }

    //如果下个页面或者上个页面没有使用到googleBuillingUtil.getInstance()，那么就需要在finish或者startActivity之前调用cleanListener()方法，来清除接口。
//可以尝试这样
    @Override
    public void onBackPressed() {
        Log.e(TGA, "onBackPressed");
//        if(TgaSdk.gameCenterCallback!=null){
//
//            TgaSdk.gameCenterCallback.onGameCenterClosed();
//        }
        super.onBackPressed();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK&& add_view.canGoBack()) {

            add_view.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        if(TgaSdk.gameCenterCallback!=null){

            TgaSdk.gameCenterCallback.onGameCenterClosed();
        }else {
            Log.e("gameCenterCallback=空了","gameCenterCallback=空了");
        }


        if (add_view != null) {
            add_view.stopLoading();
            add_view.removeAllViews();
            add_view.destroy();
            add_view = null;
        }
        super.onDestroy();
    }

    @Override
    public void codeCall(String uuid,String code) {
        if (code==null||code.equals("")){
            LoginUtils.codeEvents(add_view,uuid,false);
        }else {
            getUserCodeInfo(this,uuid,code);
        }
    }

    @Override
    public void outLoginCall() {
        WebStorage.getInstance().deleteAllData(); //清空WebView的localStorage
        SpUtils.clean(HomeActivity.this);
        SdkActivityDele.finishAllActivity();
    }
    private void getUserCodeInfo(Context context,String uuid,String code){
        String  fpId = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
        String data="{}";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fpId",fpId);
            jsonObject.put("code",code);
            data = jsonObject.toString();
            Log.e(TGA,"参数json"+data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        OkGo.<HttpBaseResult<BipGameUserInfo>>post(AppUrl.GAME_BIP_CODE_SDK_USER_INFO)
                .tag(context)
                .headers("appId",TgaSdk.appId)
                .upRequestBody(body)
                .execute(new JsonCallback<HttpBaseResult<BipGameUserInfo>>(context) {
                    @Override
                    public void onSuccess(Response<HttpBaseResult<BipGameUserInfo>> response) {
                        if (response.body().getStateCode() == 1) {
                            BipGameUserInfo resultInfo = response.body().getResultInfo();
                            LoginUtils.codeEvents(add_view,uuid,true,resultInfo.getAccessToken(),resultInfo.getRefreshToken());
                            Log.e(TGA,"获取1v1游戏列表token"+response.body().getResultInfo().getAccessToken());
                        }
                    }

                    @Override
                    public void onError(Response<HttpBaseResult<BipGameUserInfo>> response) {
                        Log.e(TGA,"获取1v1游戏列表token失败"+response.getException().getMessage());
                    }
                });
    }
}
