package sg.just4fun.tgasdk.tga.ui.my.fragment;

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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;

import sg.just4fun.tgasdk.R;
import sg.just4fun.tgasdk.callback.TGACallback;
import sg.just4fun.tgasdk.conctart.Conctant;
import sg.just4fun.tgasdk.tga.mvp.MvpFragment;
import sg.just4fun.tgasdk.tga.mvp.MvpPresenter;
import sg.just4fun.tgasdk.tga.ui.home.model.TgaSdkUserInFo;
import sg.just4fun.tgasdk.web.Conctart;
import sg.just4fun.tgasdk.web.LollipopFixedWebView;
import sg.just4fun.tgasdk.web.TgaSdk;


public class MyFragment extends MvpFragment  implements  TGACallback.ShareCallback{
   private static String TGA="MyFragment";
    public LollipopFixedWebView add_view;
    public ImageView img_loading;
    public RelativeLayout rl_loading;
    private static final String FILE_LAST_NAME = "temptext.txt";
    private String TGA_URL = "";
    private static final String FILE_DIR = "/text";
    private int isFrist=0;
    private String lang1;

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
    protected void initView(View view1) {
        super.initView(view1);
        img_loading = view1.findViewById(R.id.img_loading);
         add_view = view1.findViewById(R.id.add_view);
        rl_loading = view1.findViewById(R.id.rl_loading);
if ( TgaSdk.listener!=null){
    String lang = TgaSdk.listener.getLang();
    if(lang == null || lang.trim().equals("")) {
        String local = Locale.getDefault().toString();
        lang1= Conctart.toStdLang(local);
        upWebview(lang1);
    } else {
        //TODO: 需要开发标准化处理lang。这里暂时假定客户的APP给的已经是标准化的了
        String s = Conctart.toStdLang(lang);;
        upWebview(s);
    }

}else {
    String local = Locale.getDefault().toString();
    lang1= Conctart.toStdLang(local);
    upWebview(lang1);
}


//        getPresenter().getUserInFo(getActivity(),TGASDK.split[0]);
    }

    private void upWebview(String lang) {
        Glide.with(getActivity()).load(R.mipmap.gif)
                .into(img_loading);
        initWebView();
        rl_loading.setVisibility(View.VISIBLE);
//        String  userInfo = TgaSdk.listener.getAuthCode();
        String  userInfo = TgaSdk.listener.getUserInfo();
        Gson gson = new Gson();
        TgaSdkUserInFo tgaSdkUserInFo = gson.fromJson(userInfo, TgaSdkUserInFo.class);
        String version = Conctant.getVersion(getActivity());
        if (userInfo!=null&&tgaSdkUserInFo.getUserId()!=null){
            TGA_URL = TgaSdk.gameCentreUrl+ "?txnId="+tgaSdkUserInFo.getUserId()+"&appId="+ TgaSdk.appId+"&hidebar=1"+"&nickname="+tgaSdkUserInFo.getNickname()+"&msisdn="+tgaSdkUserInFo.getUserId()+"&appversion="+version+"&avatar="+tgaSdkUserInFo.getAvatar();//无底部
            if(lang!=null) {
                TGA_URL =TGA_URL+ "&lang=" + urlEncode(lang)+"#/me";
            }else {

                TGA_URL =TGA_URL+"#/me";
            }
        }else {
            TGA_URL =TgaSdk.gameCentreUrl;
            if(lang!=null) {
                TGA_URL =TGA_URL+ "?lang=" + urlEncode(lang)+"#/me";
            }else {
                TGA_URL =TGA_URL+ "#/me";
            }
        }
      Log.e("地址","地址="+TGA_URL);
//        https://data.just4fun.sg/h5tga/#/me/?accessToken="+token+"&refreshToken="+retoken;//有底部
        add_view.loadUrl(TGA_URL);
        add_view.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (!url.startsWith(TGA_URL) && !url.contains("paypal")) {
//                    alertDialog.show();
//                    alertDialog.getWindow().setBackgroundDrawableResource(R.color.translucent_background);
//                    alertDialog.getWindow().setLayout(ScreenUtils.getScreenWidth(HomeActivity.this), ScreenUtils.getScreenHeight(HomeActivity.this));
                }
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
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
//                  shareFaceBook(shareUri.getQueryParameter("url"));
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
                Log.d("TGA_URL", "url fininshed : " + url + ", webview.orgurl=" + webView.getOriginalUrl() +", webview.url = " + webView.getUrl());
            }
            /*@Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                alertDialog.dismiss();
            }*/
        });

        MyJavaScripface tgaBridge = new MyJavaScripface(getActivity(), TGA_URL);
        tgaBridge.init(add_view); //初始化所有需要BRIDGE的SDK
        TGACallback.setShareCallback(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            add_view.setWebContentsDebuggingEnabled(true);
        }
    }

    private void initWebView() {
        WebSettings webSetting = add_view.getSettings();
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
        webSetting.setGeolocationDatabasePath(getActivity().getDir("geolocation", 0).getPath());
//        webSetting.en
        //只获取网上数据模式
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//默认模式 根据cache-control决定是否从网络上取数据。
//        webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);

//        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            add_view.setWebContentsDebuggingEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            add_view.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    private String getUrl() {
        String str = readTxtFile();
        if (str != null && str.length() > 0) {
            return str;
        } else {
//            return "http://tga.just4fun.sg/";
            return TGA_URL;
        }
    }
    private String readTxtFile() {
        File temp = new File(getActivity().getExternalCacheDir() + FILE_DIR);//要保存文件先创建文件夹
        File tempFile = new File(temp, FILE_LAST_NAME);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (tempFile.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(tempFile);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String s = "";
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        Log.e("TestFile", "ReadTxtFile: " + line);
                        if (line != null) {
                            s += line;
                        }
                    }
                    instream.close();
                    return s;
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        TGACallback.setLangCallback(new TGACallback.LangCallback() {
            @Override
            public void getLang(String lang) {
                if (!lang.equals("")){
                    Log.e(TGA,"语言="+lang);
                    String s = Conctart.toStdLang(lang);
                    upWebview(s);
                }
            }
        });
    }

    @Override
    public void shareCall(String uuid, boolean success) {
    }

    @NonNull
    @Override
    public MvpPresenter createPresenter() {
        return null;
    }
}