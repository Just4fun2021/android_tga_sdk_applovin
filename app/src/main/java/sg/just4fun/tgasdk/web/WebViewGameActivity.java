package sg.just4fun.tgasdk.web;

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
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import sg.just4fun.tgasdk.R;
import sg.just4fun.tgasdk.adsdk.TgaAdSdkUtils;
import sg.just4fun.tgasdk.adsdk.applovin.ApplovinApiBean;
import sg.just4fun.tgasdk.adsdk.applovin.ApplovingAdJob;
import sg.just4fun.tgasdk.callback.TGACallback;
import sg.just4fun.tgasdk.conctart.SdkActivityDele;
import sg.just4fun.tgasdk.tga.base.HttpBaseResult;
import sg.just4fun.tgasdk.tga.base.JsonCallback;
import sg.just4fun.tgasdk.tga.global.AppUrl;
import sg.just4fun.tgasdk.tga.ui.home.HomeActivity;
import sg.just4fun.tgasdk.tga.utils.SpUtils;
import sg.just4fun.tgasdk.tga.view.notchscreentool.NotchScreenManager;
import sg.just4fun.tgasdk.web.login.LoginUtils;

public class WebViewGameActivity extends AppCompatActivity implements TGACallback.ShareCallback, TGACallback.CodeCallback {
    public static boolean statusaBar;
    private static String TGA="WebViewGameActivity";
    public static LollipopFixedWebView add_view;
    public static WindowManager.LayoutParams lp;
    private String url;
    private int isFrist=0;
    private LollipopFixedWebView newWebView;
    private String lang1;
    private ImageView img_loading;
    public static RelativeLayout rl_loading;
    private int change;
    private int gopag;
    private boolean navigationBar;
    private  ImageView image_black;
    private TextView tv_webtitle;
    private RelativeLayout relayout;
    private String backgroundColor;
    public static TextView tv_stuasbar;
    private String statusaBarColor;
    private int yssdk;
    public static RelativeLayout relayout_web;
    private WebViewGameActivity activity;
    public static String urlEncode(String text) {
        try{
            return URLEncoder.encode(text, "utf-8");
        } catch(Exception e) {
            return text;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint({"WrongViewCast", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_game);
        NotchScreenManager.getInstance().setDisplayInNotch(WebViewGameActivity.this);
        SdkActivityDele.addActivity(WebViewGameActivity.this);
        img_loading = findViewById(R.id.img_loading);
        rl_loading = findViewById(R.id.rl_loading);
        relayout_web = findViewById(R.id.relayout_web);

        add_view = findViewById(R.id.add_view1);
        image_black = findViewById(R.id.image_black);
        tv_webtitle = findViewById(R.id.tv_webtitle);
        relayout = findViewById(R.id.relayout);
        tv_stuasbar = findViewById(R.id.tv_stuasbar);
        activity=this;

//
//        if (Build.VERSION.SDK_INT >= 11) {
//            add_view.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
//                 }

//      banner_web = findViewById(R.id.banner_web);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        gopag = intent.getIntExtra("gopag", -1);
        statusaBar = intent.getBooleanExtra("statusaBar",true);
        navigationBar = intent.getBooleanExtra("navigationBar",false);
        backgroundColor = intent.getStringExtra("backgroundColor");
        statusaBarColor = intent.getStringExtra("statusaBarColor");
        yssdk = intent.getIntExtra("yssdk",-1);
        int statusBarHeight = getStatusBarHeight(this);
        tv_stuasbar.setHeight(statusBarHeight);
        if (statusaBarColor!=null){
            tv_stuasbar.setBackgroundColor(Color.parseColor(statusaBarColor));
        }else {
            if (yssdk==1){
                if (TgaSdk.appCode!=null&&!TgaSdk.appCode.equals("")){
                    if (TgaSdk.appCode.equals("khalaspay")){
                        statusaBarColor="#12172a";
                    }else if (TgaSdk.appCode.equals("bip")){
                        statusaBarColor="#00B1E9";
                    }else {
                        statusaBarColor="#04a7e8";
                    }
                }else {
                    statusaBarColor="#04a7e8";
                }
            }else {
                statusaBarColor="#23D3BE";
            }
            tv_stuasbar.setBackgroundColor(Color.parseColor(statusaBarColor));
        }
        if (backgroundColor!=null){
            relayout.setBackgroundColor(Color.parseColor(backgroundColor));
        }else {
            backgroundColor="#ffffff";
            relayout.setBackgroundColor(Color.parseColor(backgroundColor));

        }
            if (statusaBar){
                tv_stuasbar.setVisibility(View.VISIBLE);
            }else {
                tv_stuasbar.setVisibility(View.GONE);
            }
        full(statusaBar,this);
        image_black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (gopag==1){
            if (navigationBar){
                relayout.setVisibility(View.VISIBLE);
            }else {
                relayout.setVisibility(View.GONE);
            }
            upWebview(gopag,"",add_view);
        }else {
            relayout.setVisibility(View.GONE);
            int change = SpUtils.getInt(this, "change", -1);
            if (change==1){
                String string = SpUtils.getString(this, "changelang", "");
                upWebview(gopag,string,add_view);
            }else {
                if (TgaSdk.listener!=null){
                    String lang = TgaSdk.listener.getLang();
                    if(lang == null || lang.trim().equals("")) {
                        String local = Locale.getDefault().toString();
                        lang1= Conctart.toStdLang(local);
                        upWebview(gopag,lang1,add_view);
                    } else {
                        //TODO: 需要开发标准化处理lang。这里暂时假定客户的APP给的已经是标准化的了
                        String s = Conctart.toStdLang(lang);
                        upWebview(gopag,s,add_view);
                    }
                }else {
                    String local = Locale.getDefault().toString();
                    lang1= Conctart.toStdLang(local);
                    upWebview(gopag,lang1,add_view);
                }
            }
        }
        add_view.evaluateJavascript("", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {

            }
        });
    }
    /**
     * 获取statusBar的高度
     */
    public static int getStatusBarHeight(Context context) {
        return getDimensionPixel(context, "status_bar_height");
    }
    public static void full(boolean enable,Activity context) {
        Log.e(TGA,"掩藏显示"+enable);
        if (enable) {//显示
            WindowManager.LayoutParams attr = context.getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            context.getWindow().setAttributes(attr);
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            transparencyBar(context);
        } else {//掩藏
            Log.e(TGA,"掩藏代码执行了"+enable);
            lp =context.getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            context. getWindow().setAttributes(lp);
            context. getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
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
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window =activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void upWebview(int pag,String lang, WebView webView) {
        Glide.with(WebViewGameActivity.this).load(R.mipmap.gif)
                .into(img_loading);
        rl_loading.setVisibility(View.GONE);
        if(TgaSdk.listener!=null){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    runOnUiThread(new Runnable(){
//                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//                        @Override
//                        public void run() {
//                            String info = TgaSdk.listener.getAuthCode();
//                            SpUtils.putString(WebViewGameActivity.this,"userInfo",info);
//                            TgaSdkUserInFo userInFo = new TgaSdkUserInFo();
//                            try {
//                                JSONObject jsonObject = new JSONObject(info);
//                                userInFo.fromJson(jsonObject);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
                            if(pag==0){
                                url=url+"&lang="+lang;
                            }
                            Log.e(TGA,"lang="+lang+"   url="+url);
                            initWebView(webView);
                            Log.e(TGA,"地址"+url);
                            webView.loadUrl(url);
//                    WebViewClient pubWebViewClient=new WebViewClient(){
//                                @Override
//                                public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                                    super.onPageStarted(view, url, favicon);
//                                    rl_loading.setVisibility(View.GONE);
//                                    Log.e(TGA,"WebView"+view+"  url="+url+" Bitma="+favicon);
//                                }
//                                @Override
//                                public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                                    Uri uri = Uri.parse(url);
//                                    add_view.loadUrl(uri.toString());
//                                    Log.e(TGA,"WebView"+view+"  url="+url);
//                                    return false;
//                                }
//                                @Override
//                                public void onPageFinished(WebView webView, String url) {
//                                    super.onPageFinished(webView, url);
//                                    Log.e("执行","onPageFinished="+url);
//                                    Log.e(TGA,"WebView"+webView+"  url="+url);
//                                }
//                            };
//                            webView.setWebViewClient(pubWebViewClient);
                            webView.setWebChromeClient(new WebChromeClient() {
                                                           @Override
                                                           public void onCloseWindow(WebView window) {
                                                               super.onCloseWindow(window);
                                                               if (newWebView != null) {
                                                               }
                                                           }
                                                           @Override
                                                           public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {

                                                               Log.e("webviewOpen","onCreateWindow=");
                                                              if(newWebView==null){

                                                                  newWebView = new LollipopFixedWebView(WebViewGameActivity.this);//新创建一个webview
                                                              }

                                                               initWebView(newWebView);//初始化webview

                                                               WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;//以下的操作应该就是让新的webview去加载对应的url等操作。

                                                               transport.setWebView(newWebView);

                                                               resultMsg.sendToTarget();

                                                               return true;
                                                           }
                                @Override
                                public void onReceivedTitle(WebView view, String title) {
                                    super.onReceivedTitle(view, title);
                                    //title就是网页的title
                                    tv_webtitle.setText(title);
                                }
                        }
                            );
                        }
//                    });
//
//                }
//            }).start();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//h5谷歌调试
                webView.setWebContentsDebuggingEnabled(true);
            }
            JavaScriptinterface tgaBridge = new JavaScriptinterface(WebViewGameActivity.this, url);
            tgaBridge.init(webView); //初始化所有需要BRIDGE的SDK
            TgaAdSdkUtils.registerTgaWebview(webView);
        }
    private void initWebView(WebView webView ) {
        WebSettings webSetting = webView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(false);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getDir("geolocation", 0).getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webSetting.setMediaPlaybackRequiresUserGesture(false);//点击之后正常播放音频
        }
//        webSetting.en
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        isFrist++;
        TGACallback.setLangCallback(new TGACallback.LangCallback() {
            @Override
            public void getLang(String lang) {
                if (!lang.equals("")){
                    change=1;
                    Log.e(TGA,"语言="+lang);
                    SpUtils.putInt(WebViewGameActivity.this,"change",change);
                    String s = Conctart.toStdLang(lang);
                    SpUtils.putString(WebViewGameActivity.this,"changelang",s);
                    upWebview(gopag,s,add_view);
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        TGACallback.setShareCallback(this);
        TGACallback.setCodeCallback(this);
        if (isFrist>1){
            if(this.add_view != null) {//每次唤醒都要重新注册当前webview到广告控件
                TgaAdSdkUtils.registerTgaWebview(this.add_view);
            }
            Log.e(TGA,"第二次isFrist="+isFrist);
            TgaAdSdkUtils.flushAllEvents(this.add_view);
            Log.e(TGA,"第二次add_view="+ add_view.getUrl());
//            GoPageUtils.finishActivityEvents(add_view);
        }
    }

    //如果下个页面或者上个页面没有使用到googleBuillingUtil.getInstance()，那么就需要在finish或者startActivity之前调用cleanListener()方法，来清除接口。
//可以尝试这样
    @Override
    public void onBackPressed() {
        Log.e(TGA,"onBackPressed");
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TGA,"onStop");
        if(TGACallback.fightGameListener!=null){
            TGACallback.fightGameListener.fightGameCall();
        }
    }
    @Override
    protected void onDestroy() {
      if(add_view!=null){
           add_view.stopLoading();
           add_view.removeAllViews();
           add_view.destroy();
           add_view = null;
       }
        if (  TGACallback.codeCallback!=null){
            TGACallback.codeCallback=null;
        }
        if (  TGACallback.listener!=null){
            TGACallback.listener=null;
        }
        rl_loading=null;
        relayout_web=null;
        tv_stuasbar=null;
        if ( ApplovinApiBean.context!=null){

            ApplovinApiBean.context=null;
        }
        if ( ApplovingAdJob.apiBean!=null){

            ApplovingAdJob.apiBean=null;
        }

        SdkActivityDele.finishActivity(WebViewGameActivity.this);
        super.onDestroy();
    }

    @Override
    public void shareCall(String uuid, boolean success) {
        Log.e(TGA,"webvigame="+uuid+" 成功=="+success);
    }
    @Override
    public void codeCall(String uuid,String userInfo) {
        if (userInfo==null||userInfo.equals("")){
            LoginUtils.codeEvents(add_view,uuid,false);
        }else {
//            getUserCodeInfo(this,uuid,code);

            try {
                String s2 = TgaSdk.toEncryptData(userInfo,TgaSdk.appKey);
                getUserCode(uuid,s2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
//        BipGameUserInfo
        OkGo.<String>post(AppUrl.GAME_BIP_CODE_SDK_USER_INFO)
                .tag(context)
                .headers("appId",TgaSdk.appId)
                .upRequestBody(body)
                .execute(new JsonCallback<String>(context) {
                    @Override
                    public void onSuccess(Response  response) {

                        String s1 = response.body().toString();
//                        s1="{\"stateCode\":1,\"resultInfo\":{\"totalCount\":0,\"desc\":\"SUCCESS\",\"itemCount\":0}}";
                        Log.e(TGA,"初始化成功的="+s1);
                        Gson gson = new GsonBuilder()
                                .serializeNulls()
                                .create();
                        HttpBaseResult httpBaseResult = gson.fromJson(s1, HttpBaseResult.class);
                        if (httpBaseResult.getStateCode() == 1) {
                            String s = gson.toJson(httpBaseResult.getResultInfo());
                            try {
                                JSONObject jsonObject1 = new JSONObject(s);
                                String accessToken = jsonObject1.getString("accessToken");
                                String refreshToken = jsonObject1.getString("refreshToken");
                                LoginUtils.codeEvents(add_view,uuid,true,accessToken,refreshToken);
                                Log.e(TGA,"获取1v1游戏列表token"+accessToken);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        Log.e(TGA,"获取1v1游戏列表token失败"+response.getException().getMessage());
                    }
                });
    }

    private  void getUserCode(String uuid, String encryptData){

        JSONObject jsonObject = new JSONObject();
        String data = "{}";
        try {
            jsonObject.put("encryptData", encryptData);
            jsonObject.put("appId", TgaSdk.appId);
            data = jsonObject.toString();
            Log.e(TGA,"获取code=data="+data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        OkGo.<String>post(AppUrl.GET_USER_CODE)
                .tag(WebViewGameActivity.this)
                .upRequestBody(body)
                .execute(new JsonCallback<String>(WebViewGameActivity.this) {
                    @Override
                    public void onSuccess(Response response) {
                        String s1 = response.body().toString();
//                        s1="{\"stateCode\":1,\"resultInfo\":{\"totalCount\":0,\"desc\":\"SUCCESS\",\"itemCount\":0}}";
                        Log.e(TGA,"获取code=data="+s1);
                        Gson gson1 = new GsonBuilder()
                                .serializeNulls()
                                .create();
                        HttpBaseResult httpBaseResult = gson1.fromJson(s1, HttpBaseResult.class);
                        if (httpBaseResult.getStateCode() == 1) {
                            String s = gson1.toJson(httpBaseResult.getResultInfo());
                            try {
                                Log.e(TGA,"gameif="+s);
                                JSONObject jsonObject1 = new JSONObject(s);
                                String code = jsonObject1.getString("code");
                                getUserCodeInfo(WebViewGameActivity.this,uuid,code);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TGA,"data是不是空="+e.getMessage());
                            }

                        }
                    }
                    @Override
                    public void onError(Response response) {
                        Log.e(TGA,"1V1游戏列表数据失败="+response.getException().getMessage());

                    }
                });
    }
}