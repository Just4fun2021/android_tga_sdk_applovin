package sg.just4fun.tgasdk.web;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import sg.just4fun.tgasdk.R;
import sg.just4fun.tgasdk.callback.TGACallback;
import sg.just4fun.tgasdk.conctart.Conctant;
import sg.just4fun.tgasdk.modle.AdConfigBean;
import sg.just4fun.tgasdk.modle.AppConfig;
import sg.just4fun.tgasdk.modle.BipGameUserInfo;
import sg.just4fun.tgasdk.modle.BipGameUserUser;
import sg.just4fun.tgasdk.modle.GameListInfoBean;
import sg.just4fun.tgasdk.modle.GameinfoBean;
import sg.just4fun.tgasdk.modle.GooglePayInfo;
import sg.just4fun.tgasdk.modle.GooglePayInfoBean;
import sg.just4fun.tgasdk.modle.UserInFoBean;
import sg.just4fun.tgasdk.tga.base.HttpBaseResult;
import sg.just4fun.tgasdk.tga.base.JsonCallback;
import sg.just4fun.tgasdk.tga.global.AppUrl;
import sg.just4fun.tgasdk.tga.global.Global;
import sg.just4fun.tgasdk.tga.ui.home.HomeActivity;
import sg.just4fun.tgasdk.tga.ui.home.model.RegisterBean;
import sg.just4fun.tgasdk.tga.ui.home.model.TgaSdkUserInFo;
import sg.just4fun.tgasdk.tga.utils.SpUtils;
//TGASDK初始化类
public class TgaSdk {
    public static Context mContext;
    public static  TGACallback.TgaEventListener listener;
    public static List<AdConfigBean> appConfigbeanList=new ArrayList<AdConfigBean>();
    public static String appKey;
    public static String appId;
    public static String appPaymentKey;
    public static List<GooglePayInfo> infoList=new ArrayList<>();
    public static  String appConfigList;
    public static TGACallback.initCallback initCallback;
    public static String applovnIdConfig;
    public static String gameCentreUrl;
    private static String TGA="TgaSdk";
    private static String sdkPkName;
    private static int isSuccess=0;
    public static String iconpath;
    public static String packageName;
    public static String schemeUrl;
    private static String skdjskd;
    private static String lang;
    public static List<GameinfoBean> gameif=new ArrayList<>();
    public static String bipUserid;
    public static String bipToken;
    public static String rebipToken;
    public static TGACallback.GameCenterCallback gameCenterCallback;
    public static String appCode;
    public static String txnid="";
    public static String msisid="";
    public static String env;
    private static String infoUrl;
    private static String googlepayUrl;
    private static String gamelistUrl;
    private static String loginsdkUrl;
    private static String userinfoUrl;
    private static String theme1;
    private static String theme;

    private TgaSdk() {

    }
    public static String urlEncode(String text) {
        try{
            return URLEncoder.encode(text, "utf-8");
        } catch(Exception e) {
            return text;
        }
    }

    //TGASDK初始化方法
    public static void init(Context context,String appKey,String schemeUrl,String appPaymentKey,TGACallback.TgaEventListener listener,TGACallback.initCallback initCallback) {
        init(context,"",appKey,schemeUrl,appPaymentKey,listener,initCallback);
    }

    //TGASDK初始化方法
    public static void init(Context context,String env,String appKey,String schemeUrl,String appPaymentKey,TGACallback.TgaEventListener listener,TGACallback.initCallback initCallback) {
        TgaSdk.env=env;
        mContext = context.getApplicationContext();
        TgaSdk.appKey= appKey;
        TgaSdk.schemeUrl= schemeUrl;
        TgaSdk.listener = listener;
        Log.e(TGA,"listener是不是空了="+listener);
        TgaSdk.initCallback=initCallback;
        Log.e(TGA,"linitCallback是不是空了="+initCallback);
        TgaSdk.appPaymentKey = appPaymentKey;
        Log.e(TGA,"appPaymentKey是不是空了="+appPaymentKey);
//       获取用户配置表
        getUserInfo(appKey);
    }


    // TGASDK拉取google支付配置
    private static void getGooglePayInfo(String appId) {
        JSONObject jsonObject = new JSONObject();
        String data = "{}";
        try {
            jsonObject.put("appId", appId);
            data = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        if(env.equals("bip")){
            googlepayUrl= AppUrl.BIP_GET_GOOGLEPAY_INFO;
        }else {
            googlepayUrl= AppUrl.GET_GOOGLEPAY_INFO;
        }
//
        OkGo.<String>post(googlepayUrl)
                .tag(mContext)
                .upRequestBody(body)
                .execute(new JsonCallback<String>(mContext) {
                    @Override
                    public void onSuccess(Response response) {
                        String s1 = response.body().toString();
                        Log.e(TGA,"初始化成功的="+s1);
                        Gson gson = new GsonBuilder()
                                .serializeNulls()
                                .create();
                        HttpBaseResult httpBaseResult = gson.fromJson(s1, HttpBaseResult.class);
                        if (httpBaseResult.getStateCode() == 1) {
                            try {
                                String s = gson.toJson(httpBaseResult.getResultInfo());
                                JSONObject jsonObject1 = new JSONObject(s);
                                JSONArray data1 = jsonObject1.getJSONArray("data");
                                if (data1!=null&&data1.length()>0){
                                    for (int a=0;a<data1.length();a++){
                                        String o = String.valueOf(data1.get(a));
                                        GooglePayInfo googlePayInfo = gson.fromJson(o, GooglePayInfo.class);
                                        infoList.add(googlePayInfo);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Log.e(TGA,"google支付配置="+infoList);
                        }
                    }
                    @Override
                    public void onError(Response response) {
                        Log.e(TGA,"google支付配置失败="+response.message());
                    }
                });
    }
    // TGASDK获取游戏token
    private static void userCodeLogin(String pkName,UserInFoBean resultInfo,Gson gson){
        String  fpId = Settings.System.getString(mContext.getContentResolver(), Settings.System.ANDROID_ID);

        String data="{}";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("fpId",fpId);
            if (listener!=null){
                String userInfo = listener.getAuthCode();
                jsonObject.put("code",userInfo);
            }
            data = jsonObject.toString();
            Log.e(TGA,"参数json"+data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        if(TgaSdk.env.equals("bip")){
            userinfoUrl= AppUrl.BIP_GAME_BIP_CODE_SDK_USER_INFO;
        }else {
            userinfoUrl= AppUrl.GAME_BIP_CODE_SDK_USER_INFO;
        }
//        BipGameUserInfo
        OkGo.<String>post(userinfoUrl)
                .tag(mContext)
                .headers("appId",appId)
                .upRequestBody(body)
                .execute(new JsonCallback<String>(mContext) {
                    @Override
                    public void onSuccess(Response response) {
                        String s1 = response.body().toString();
                        HttpBaseResult httpBaseResult = gson.fromJson(s1, HttpBaseResult.class);
                        if (httpBaseResult.getStateCode() == 1) {

                            svanTokenInfo(gson.toJson(httpBaseResult.getResultInfo()));

                            initCodeTokenInfo(pkName, resultInfo, gson, R.string.packagename, R.string.packagename);
//
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        Log.e(TGA,"获取1v1游戏列表token失败"+response.getException().getMessage());
                    }
                });
    }

    private static void initCodeTokenInfo(String pkName, UserInFoBean resultInfo, Gson gson, int p, int p2) {
        if (packageName != null && !packageName.equals("")) {
            if (packageName.equals(pkName)) {
                iconpath = resultInfo.getIconpath();
                appConfigList = resultInfo.getAppConfig();
                if (appConfigList != null && !appConfigList.equals("") && !appConfigList.equals("{}")) {
                    AppConfig adConfigBean = gson.fromJson(appConfigList, AppConfig.class);
                    try {
                        gameCentreUrl = Objects.requireNonNull(requireNotBlankString(adConfigBean.getGameCentreUrl()));
                        Log.e("游戏列表url","游戏列表url="+gameCentreUrl);
                    } catch (Exception e) {
                        gameCentreUrl = Global.TEST_MOREN;
                    }
                    try {
                        appCode = Objects.requireNonNull(requireNotBlankString(adConfigBean.getAppCode()));
                        Log.e("游戏列表url","appCode="+appCode);
                    } catch (Exception e) {
                        appCode ="";
                    }
                    if (adConfigBean != null) {
                        List<AdConfigBean> adList = adConfigBean.getAd();
                        if (adList == null || adList.isEmpty()) {
                            Log.e("tgasdk", "ad配置表isEmpty==" + adList.size());
                            applovnIdConfig = null;
                        } else {
                            Log.e("tgasdk", "ad配置表==" + adList.size());
                            try {
                                Log.e("tgasdk", "ad配置表applovnIdConfig==" + applovnIdConfig);
                                applovnIdConfig = adList.get(0).getConfig().toJson().toString();
                            } catch (Exception e2) {
                                applovnIdConfig = null;
                                Log.e("tgasdk", "ad配置表Exception==" + adList.size());
                            }
                            Log.e("tgasdk", "ad配置表adList==" + applovnIdConfig);
                        }
                    }
                } else {
                    gameCentreUrl = Global.TEST_MOREN;
                }
                getGooglePayInfo(appId);
                Log.e("tgasdk", "ad配置表==" + applovnIdConfig);
                isSuccess = 1;
                initCallback.initSucceed();
            } else {
                Log.e(TGA, "包名不一致=");
                isSuccess = 0;
                initCallback.initError(mContext.getResources().getString(p));
            }
        } else {
            Log.e(TGA, "包名不一致=");
            isSuccess = 0;
            initCallback.initError(mContext.getResources().getString(p2));
        }
    }

    // TGASDK获取游戏token
    private static void gameUserLogin(String pkName,UserInFoBean resultInfo,Gson gson){
        String  fpId = Settings.System.getString(mContext.getContentResolver(), Settings.System.ANDROID_ID);
        String data="{}";
        JSONObject jsonObject = new JSONObject();
        try {
//            if (listener!=null){
//                String userInfo = listener.getUserInfo();
//                TgaSdkUserInFo userInFo = new TgaSdkUserInFo();
//                try {
//                    JSONObject jsonObject1 = new JSONObject(userInfo);
//                    userInFo.fromJson(jsonObject1);
//                    jsonObject.put("txnId",userInFo.getUserId());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            jsonObject.put("fpId",fpId);
            jsonObject.put("appId",appId);
            data = jsonObject.toString();
            Log.e(TGA,"参数json"+data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        if(TgaSdk.env.equals("bip")){
            loginsdkUrl= AppUrl.BIP_GAME_BIP_LOGIN_SDK;
        }else {
            loginsdkUrl= AppUrl.GAME_BIP_LOGIN_SDK;
        }
        OkGo.<String>post(loginsdkUrl)
                .tag(mContext)
                .upRequestBody(body)
                .execute(new JsonCallback<String>(mContext) {
                    @Override
                    public void onSuccess(Response response) {
                        String s1 = response.body().toString();
                        HttpBaseResult httpBaseResult = gson.fromJson(s1, HttpBaseResult.class);
                        if (httpBaseResult.getStateCode() == 1) {
                            svanTokenInfo(gson.toJson(httpBaseResult.getResultInfo()));
                            Log.e(TGA,"获取1v1游戏列表token");
                            initCodeTokenInfo(pkName, resultInfo, gson, R.string.packagename, R.string.packagename);
                        }
                    }

                    @Override
                    public void onError(Response response) {
                        Log.e(TGA,"获取1v1游戏列表token失败"+response.getException().getMessage());
                    }
                });
    }

    private static void svanTokenInfo(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject user = jsonObject.getJSONObject("user");
            bipToken = jsonObject.getString("accessToken");
            rebipToken = jsonObject.getString("refreshToken");
            String txnId = user.getString("txnId");
            bipUserid= user.getString("id");
            SpUtils.putString(mContext,"bipTxnId",txnId);
            SpUtils.putString(mContext,"bipToken", bipToken);
            SpUtils.putString(mContext,"bipUserId",bipUserid);
            SpUtils.putString(mContext,"reBipToken",rebipToken);
            getGameListHttp(appId, bipToken);
            String name = user.getString("name");
            String header = user.getString("header");
            SpUtils.putString(mContext,"bipHeader",header);
            SpUtils.putString(mContext,"bipName",name);

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }


    // TGASDK拉取游戏列表
    private static void getGameListHttp(String appId,String accessToken) {
        String lang1 = listener.getLang();
        if (lang1==null||lang1.equals("")){
            String local = Locale.getDefault().toString();
            lang= Conctart.toStdLang(local);
        }else {
            lang=lang1;
        }
        JSONObject jsonObject = new JSONObject();
        String data = "{}";
        try {
            jsonObject.put("tag", "battle_1v1");
            data = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);

        if(TgaSdk.env.equals("bip")){
            gamelistUrl= AppUrl.BIP_GET_GAME_LIST;
        }else {
            gamelistUrl= AppUrl.GET_GAME_LIST;
        }
//
        OkGo.<String>post(gamelistUrl)
                .tag(mContext)
                .headers("Authorization",accessToken)//
                .headers("appId",appId)//
                .headers("lang",lang)
                .upRequestBody(body)
                .execute(new JsonCallback<String>(mContext) {
                    @Override
                    public void onSuccess(Response response) {
                        String s1 = response.body().toString();
//                        s1="{\"stateCode\":1,\"resultInfo\":{\"totalCount\":0,\"desc\":\"SUCCESS\",\"itemCount\":0}}";
                        Log.e(TGA,"初始化成功的="+s1);
                        Gson gson = new GsonBuilder()
                                .serializeNulls()
                                .create();
                        HttpBaseResult httpBaseResult = gson.fromJson(s1, HttpBaseResult.class);
                        if (httpBaseResult.getStateCode() == 1) {
                            String s = gson.toJson(httpBaseResult.getResultInfo());
//                            GameListInfoBean resultInfo = gson.fromJson(s, GameListInfoBean.class);
                            try {
                                Log.e(TGA,"gameif="+s);
                                JSONObject jsonObject1 = new JSONObject(s);

                                JSONArray data1 = jsonObject1.getJSONArray("data");
                                if(data1!=null&&data1.length()>0){
                                    gameif.clear();
                                    for (int a=0;a<data1.length();a++){
                                        String o = String.valueOf(data1.get(a));
                                        gameif.add(gson.fromJson(o,GameinfoBean.class));
                                    }
                                    Log.e(TGA,"1V1游戏列表数据="+gameif.size());
                                    String lang1 = listener.getLang();
                                    if (lang1==null||lang1.equals("")){
                                        String local = Locale.getDefault().toString();
                                        lang= Conctart.toStdLang(local);
                                    }else {
                                        lang=lang1;
                                    }
                                    for (int a=0;a<gameif.size();a++){
                                        Log.e(TGA,"1V1游戏列表数据="+gameif.get(a).getName());
                                        String s2 = Conctant.gameName(lang, gameif.get(a).getName());
                                        gameif.get(a).setName(s2);
                                        Log.e(TGA,"1V1游戏列表数据name="+s2);
                                        String s3 = Conctant.gameName(lang, gameif.get(a).getRemark());
                                        gameif.get(a).setRemark(s3);
                                        Log.e(TGA,"1V1游戏列表数据remark="+s3);
                                    }
                                }else {
                                    Log.e(TGA,"data是不是空=空了");
                                }

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

    public static Context getContext() {
        return mContext;
    }

    //进入TGAsdk游戏中心方法
    public static void goPage(Context context,String theme, String url, boolean autoToken, String schemeQuery, boolean navigationbar) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                String yhAppId = SpUtils.getString(mContext, "yhAppId", "");
        if (theme==null||theme.equals("")){
            theme1=appCode;
        }else {
            theme1=theme;
        }
        String bipHeader = SpUtils.getString(mContext, "bipHeader", "");
        String bipName = SpUtils.getString(mContext, "bipName", "");
        String bipTxnId = SpUtils.getString(mContext, "bipTxnId", "");
        String bipToken = SpUtils.getString(mContext, "bipToken", "");
        String reBipToken = SpUtils.getString(mContext, "reBipToken", "");
        if (url==null||url.equals("")){
            String version = Conctant.getVersion(mContext);
            if (isSuccess==1){
                Log.e(TGA,"isSuccess==1");
                if (TgaSdk.listener!=null){
                    Log.e(TGA,"TgaSdk.listener不为空");
                    String userInfo = TgaSdk.listener.getAuthCode();
                    if(bipToken==null||bipToken.equals("")){
                        Log.e(TGA,"bipToken="+bipToken);
//                                  "&txnId=1&msisdn=1"+
                        url= TgaSdk.gameCentreUrl+"?appId="+TgaSdk.appId+"&theme="+theme1+"&navigationbar="+navigationbar+"&token="+bipToken+"&refresh-token="+reBipToken;//无底部
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.putExtra("url",url);
                        intent.putExtra("gopag",0);
                        intent.putExtra("yssdk",1);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        return;
                    }else {
//                                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//                                TgaSdkUserInFo userInFo = gson.fromJson(userInfo, TgaSdkUserInFo.class);
                        Log.e(TGA,"游戏中心列表="+TgaSdk.gameCentreUrl);
                        if (TgaSdk.gameCentreUrl==null||TgaSdk.gameCentreUrl.equals("")){
                            TgaSdk.gameCentreUrl= Global.TEST_MOREN;
                        }
                        Log.e("rebipToken没有","rebipToken=="+reBipToken);

                        if (bipTxnId!=null&&!bipTxnId.equals("")){
                            txnid= "txnId="+ bipTxnId;
                            msisid="&msisdn="+bipTxnId;
                        }else {
                            txnid="";
                            msisid="";
                        }

                        if (schemeQuery!=null&&!schemeQuery.equals("")){
                            url= TgaSdk.gameCentreUrl+ "?"+txnid+"&theme="+theme1+"&"+schemeQuery+"&navigationbar="+navigationbar+"&appId="+ TgaSdk.appId+"&nickname="+bipName+msisid+"&token="+bipToken+"&refresh-token="+reBipToken+"&appversion="+version+"&avatar="+bipHeader;//无底部
                        }else {
                            url= TgaSdk.gameCentreUrl+ "?"+txnid+"&theme="+theme1+"&appId="+ TgaSdk.appId+"&navigationbar="+navigationbar+"&nickname="+bipName+"&token="+bipToken+"&refresh-token="+reBipToken+msisid+"&appversion="+version+"&avatar="+bipHeader;//无底部
                        }
                        Intent intent = new Intent(context, HomeActivity.class);
                        intent.putExtra("url",url);
                        intent.putExtra("gopag",0);
                        intent.putExtra("yssdk",1);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        return;
                    }
                }else {
                    Log.e(TGA,"TgaSdk.listener=null");
                    initCallback.initError(mContext.getResources().getString(R.string.sdkiniterror));
                    return;
                }
            }else {
                Log.e(TGA,"初始化.isSuccess=0");
                initCallback.initError(mContext.getResources().getString(R.string.sdkiniterror));
                return;
            }
        }
        Intent intent = new Intent(context, HomeActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("yssdk",1);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
//            }
//        }).start();

    }
    public static String buildUserInfo(String userId, String nickName, String headImg) {
        TgaSdkUserInFo  userInFo = new TgaSdkUserInFo(userId, nickName, headImg);
        try {
            return userInFo.toJson().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getSchemeQuery (String query) {
        TgaSdkUserInFo  userInFo = new TgaSdkUserInFo(query);
        try {
            return userInFo.toJson().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    //跳转游戏中心
    public static void goPage(Context context,String theme2,String url,String gameid,boolean navigationbar) {
        if (theme2!=null&&!theme2.equals("")){
            theme = Conctant.themeCorol(theme2);
        }else {
            theme=theme2;
        }
        goPage(context, theme,url, true,gameid,navigationbar);
    }
    //跳转游戏中心
    public static void openGameCenter(Context context,String theme2, boolean navigationbar, TGACallback.GameCenterCallback gameCenterCallback) {
        TgaSdk.gameCenterCallback=gameCenterCallback;
        if (theme2!=null&&!theme2.equals("")){
            theme = Conctant.themeCorol(theme2);
        }else {
            theme=theme2;
        }
        goPage(context, theme,"",true,"",navigationbar);
    }
    //跳转游戏中心
    public static void goLink(Context context,String url,boolean navigationbar) {
        goPage(context,"", url,true,"",navigationbar);
    }
    public static void shareSuccess(String uuid) {
        shared(uuid, true);
    }
    public static void shareError(String uuid) {
        shared(uuid, false);
    }
    public static void shared(String uuid, boolean success) {
        if (TGACallback.listener!=null){
            TGACallback.listener.shareCall(uuid, success);
        }
    }
    public static void onUserLogined(String uuid,String code) {
        if (TGACallback.codeCallback!=null){

            TGACallback.codeCallback.codeCall(uuid,code);
        }
    }
    public static void onLogout() {
        if(TGACallback.outLoginCallback!=null){
            TGACallback.outLoginCallback.outLoginCall();
        }

    }

    public static void lang(String lang) {
        if (TGACallback.langListener!=null){
            TGACallback.langListener.getLang(lang);
        }
    }

    public static void shared(String uuid, int successCount) {
        shared(uuid, successCount > 0);
    }

    public static String requireNotBlankString(String value) {
        if(value == null || value.trim().equals("")) {
            return null;
        }
        return value;
    }

    //拉取SDK配置表
    public static void getUserInfo(String appKe){
        JSONObject jsonObject = new JSONObject();
        String data = "{}";
        try {
            jsonObject.put("appSdkKey", appKe);
            data = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, data);
        if(env.equals("bip")){
            infoUrl= AppUrl.BIP_TGA_SDK_INFO;
        }else {
            infoUrl= AppUrl.TGA_SDK_INFO;
        }
        OkGo.<String>post(infoUrl)
                .tag(mContext)
                .upRequestBody(body)
                .execute(new JsonCallback<String>(mContext) {
                    @Override
                    public void onSuccess(Response response) {
                        String s1 = response.body().toString();
                        Log.e(TGA,"初始化成功的="+s1);
                        Gson gson = new GsonBuilder()
                                .serializeNulls()
                                .create();
                        HttpBaseResult httpBaseResult = gson.fromJson(s1, HttpBaseResult.class);
                        Log.e(TGA,"初始化成功的="+httpBaseResult.getStateCode());
                        Log.e(TGA,"resultInfo内容="+gson.toJson(httpBaseResult.getResultInfo()));
                        try{
                            if (httpBaseResult.getStateCode() == 1) {
                                Log.e(TGA,"初始化成功的=");
                                if (listener!=null){
                                    Log.e(TGA,"listener是不是空了="+gson.toJson(httpBaseResult.getResultInfo()));
                                    String s = gson.toJson(httpBaseResult.getResultInfo());
                                    UserInFoBean resultInfo = gson.fromJson(s, UserInFoBean.class);
                                    Log.e(TGA,"listener是不是空了resultInfo1"+gson.toJson(httpBaseResult.getResultInfo()));
                                    String pkName = mContext.getPackageName();
                                    packageName = resultInfo.getPackageName();
                                    //包名相等
                                    appId = resultInfo.getAppId();
//                            获取游戏token
//                            gameUserLogin();
//                            通过code获取用户信息
                                    String userInfo = listener.getAuthCode();
                                    SpUtils.putString(mContext,"yhAppId",appId);
                                    if(userInfo!=null&&!userInfo.equals("")){
                                        userCodeLogin(pkName,resultInfo,gson);
                                    }else {
                                        gameUserLogin(pkName,resultInfo,gson);
                                    }
                                }else {
                                    isSuccess=0;
                                    initCallback.initError("TgaEventListener接口为空");
                                }
                            } else {
                                isSuccess=0;
                                initCallback.initError("服务端errorCode=" + httpBaseResult.getStateCode());
                            }
                        } catch(Exception e) {
                            isSuccess = 0;
                            initCallback.initError("errorCode=" + -5);
                            Log.e("tgasdk", "initiate failed", e);
                        }
                    }
                    @Override
                    public void onError(Response response) {
                        isSuccess=0;
                        initCallback.initError("errorCode=" + -5);
                        Log.e("初始化", "充值失败=" + response.getException().getMessage());
                    }
                });
    }
    public static void goPageByScheme(Uri schemeUri,boolean navigationbar){
        if (schemeUri!=null||!schemeUri.equals("")){
            try{
                String query = schemeUri.getQuery();
                goPage(mContext, "","",true,query,navigationbar);
            }catch (Exception e){
                initCallback.initError("schemeUri存在异常");
            }
        }else {
            goPage(mContext,"","",null,navigationbar);
        }
    }

    public static List<GameinfoBean> getGameList(){
        Log.e(TGA,"1V1游戏列表数据getGameList="+new Gson().toJson(gameif));
        return gameif;
    }

    public static String getBipUserInfo(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", bipUserid);
            jsonObject.put("token", bipToken);
            jsonObject.put("retoken", rebipToken);
            return jsonObject.toString() ;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "获取错误";
    }

}
