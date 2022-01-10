package sg.just4fun.tgasdk.tga.ui.home.fragment;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

//import com.android.billingclient.api.SkuDetails;
//import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import sg.just4fun.tgasdk.R;
import sg.just4fun.tgasdk.adsdk.TgaAdSdkUtils;
import sg.just4fun.tgasdk.adsdk.applovin.ApplovinApiBean;
import sg.just4fun.tgasdk.callback.TGACallback;
import sg.just4fun.tgasdk.conctart.Conctant;
import sg.just4fun.tgasdk.tga.mvp.MvpFragment;
import sg.just4fun.tgasdk.tga.mvp.MvpPresenter;
import sg.just4fun.tgasdk.tga.ui.home.model.TgaSdkUserInFo;
import sg.just4fun.tgasdk.tga.utils.SpUtils;
import sg.just4fun.tgasdk.web.Conctart;
import sg.just4fun.tgasdk.web.JavaScriptinterface;
import sg.just4fun.tgasdk.web.LollipopFixedWebView;
import sg.just4fun.tgasdk.web.TgaSdk;
import sg.just4fun.tgasdk.web.goPage.GoPageUtils;


public class HomeFragment extends MvpFragment implements  TGACallback.ShareCallback {
    public static String TGA="Home";
    private String lin_url;
    public LollipopFixedWebView add_view;
    public ImageView img_loading;
    public RelativeLayout rl_loading;
    private int isFrist=0;
    private String TGA_URL = "";
//    private List<SkuDetails> mSkuDetails=new ArrayList<>();
    private LollipopFixedWebView newWebView;
    private ApplovinApiBean apploving;
    private JavaScriptinterface tgaBridge;
    private String lang1;
    private int change;

    public HomeFragment(String url) {
        lin_url=url;
    }
    public HomeFragment() {
    }
    @Override
    protected int getLayout() {
        return R.layout.tgasdk_webview_layout;
    }


    @Override
    public void initImmersionBar() {

    }

    public static String urlEncode(String text) {
        try{
            return URLEncoder.encode(text, "utf-8");
        } catch(Exception e) {
            return text;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void initView( View view) {
        super.initView(view);
        add_view =view.findViewById(R.id.add_view);
        img_loading = view.findViewById(R.id.img_loading);
        rl_loading = view.findViewById(R.id.rl_loading);
        int homeChange = SpUtils.getInt(getActivity(), "homeChange", -1);
        if (homeChange==1){
            String homeChange1 = SpUtils.getString(getActivity(), "homeChange", "");
            upWebview(homeChange1);
        }else {
            if(TgaSdk.listener!=null){
                String lang = TgaSdk.listener.getLang();
                if(lang == null || lang.trim().equals("")) {
                    String local = Locale.getDefault().toString();
                    lang1= Conctart.toStdLang(local);
                    Log.e("系统语言","系统语言="+lang1);
                    upWebview(lang1);
                } else {
                    //TODO: 需要开发标准化处理lang。这里暂时假定客户的APP给的已经是标准化的了
                    String s = Conctart.toStdLang(lang);
                    upWebview(s);
                }
            }else {
                String local = Locale.getDefault().toString();
                lang1= Conctart.toStdLang(local);
                upWebview(lang1);
            }

        }
        TGACallback.setShareCallback(this);
    }

    private void upWebview(String lang) {
        Glide.with(getActivity()).load(R.mipmap.gif)
                .into(img_loading);
        initWebView(add_view);
        rl_loading.setVisibility(View.VISIBLE);

//        String userInfo = TgaSdk.listener.getAuthCode();
//        Log.e("头像","头像="+userInfo);
//        SpUtils.putString(getActivity(),"userInfo",userInfo);
        Gson gson = new Gson();
//        TgaSdkUserInFo tgaSdkUserInFo = gson.fromJson(userInfo, TgaSdkUserInFo.class);
//        Log.e("头像","头像="+tgaSdkUserInFo.getAvatar()+"  userid="+tgaSdkUserInFo.getUserId()+" 昵称="+tgaSdkUserInFo.getNickname()+" 游戏="+tgaSdkUserInFo.getGameId());
//        if (tgaSdkUserInFo.getUserId()==null||tgaSdkUserInFo.getUserId().equals("")){
//            TgaSdk.listener.quitLogin(getActivity());
//            return;
//        }
        String version = Conctant.getVersion(getActivity());
        Log.e("啥呀","啥呀="+TgaSdk.gameCentreUrl);
//        TGA_URL = "https://data.just4fun.sg/h5tga4bip/?txn_id=18&msisdn=456&togameid=56&lang=tr&appversion=3.76";
        if (lin_url==null||lin_url.equals("")){
//            if (tgaSdkUserInFo!=null&&tgaSdkUserInFo.getUserId()!=null){
//                if (tgaSdkUserInFo.getGameId()!=null&&!tgaSdkUserInFo.getGameId().equals("")){
//                    TGA_URL = TgaSdk.gameCentreUrl + "?txnId="+tgaSdkUserInFo.getUserId()+"&appId="+ TgaSdk.appId+"&hidebar=1" +"&lang="+lang+"&nickname="+tgaSdkUserInFo.getNickname()+"&"+tgaSdkUserInFo.getGameId()+"&msisdn="+tgaSdkUserInFo.getUserId()+"&appversion="+version+"&avatar="+tgaSdkUserInFo.getAvatar();//无底部
//                }else {
//
//                    TGA_URL =TgaSdk.gameCentreUrl + "?txnId="+tgaSdkUserInFo.getUserId()+"&appId="+ TgaSdk.appId+"&hidebar=1" +"&lang="+lang+"&nickname="+tgaSdkUserInFo.getNickname()+"&msisdn="+tgaSdkUserInFo.getUserId()+"&appversion="+version+"&avatar="+tgaSdkUserInFo.getAvatar();//无底部
//                }
//            }else {
//                TGA_URL = TgaSdk.gameCentreUrl;
//                if(lang!=null) {
//                    TGA_URL += "?lang=" + urlEncode(lang);
//                }
//            }
        }else {
            TGA_URL= lin_url+"&lang="+lang;

        }
        Log.e("地址","TGA_URL="+TGA_URL);
        add_view.loadUrl(TGA_URL);
        add_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("地址","进入h5页面="+TGA_URL);
                if (!url.startsWith(TGA_URL) && !url.contains("paypal")) {
//                    alertDialog.show();
//                    alertDialog.getWindow().setBackgroundDrawableResource(R.color.translucent_background);
//                    alertDialog.getWindow().setLayout(ScreenUtils.getScreenWidth(HomeActivity.this), ScreenUtils.getScreenHeight(HomeActivity.this));
                }
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                Log.e("地址","h5页面="+TGA_URL);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//h5谷歌调试
            add_view.setWebContentsDebuggingEnabled(true);
        }
        tgaBridge = new JavaScriptinterface(getActivity(), TGA_URL);
        tgaBridge.init(add_view); //初始化所有需要BRIDGE的SDK
        TgaAdSdkUtils.registerTgaWebview(this.add_view); //通知广告控件将add_view注册到webview
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
        webSetting.setAppCachePath(getActivity().getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(getActivity().getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(getActivity().getDir("geolocation", 0)
                .getPath());
//只获取网上上数据模式
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//默认模式 根据cache-control决定是否从网络上取数据。
//        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (isFrist>1){
            if(this.add_view != null) { //每次唤醒都要重新注册当前webview到广告控件
                TgaAdSdkUtils.registerTgaWebview(this.add_view);
            }
            GoPageUtils.finishActivityEvents(add_view);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        isFrist++;
TGACallback.setLangCallback(new TGACallback.LangCallback() {
    @Override
    public void getLang(String lang) {
      if (!lang.equals("")){
          change=1;
          SpUtils.putInt(getActivity(),"homeChange",change);
          Log.e(TGA,"语言="+lang);
          String s =Conctart.toStdLang(lang);
          SpUtils.putString(getActivity(),"homeChange",s);
          upWebview(s);
      }
    }
});

//        try {
//            Thread.sleep(5000);
//            tgaBridge.showApplovinAd("sss", "fullscreen");//播放applovin广告
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }
    @Override
    public void shareCall(String uuid,boolean success) {
        Log.e("回调","shareCall="+uuid+" 成功=="+success);
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return null;
    }

}