package sg.just4fun.tgasdk.callback;


import android.content.Context;

import sg.just4fun.tgasdk.modle.UserInFoBean;

public class TGACallback {
    public static ShareCallback listener;
    public static LangCallback langListener;
    public static FightGameCallback fightGameListener;
    public static CodeCallback codeCallback;
    public static OutLoginCallback outLoginCallback;


    public interface initCallback {
        void initSucceed();

        void initError(String error);
    }

    public interface TgaEventListener {
        default void quitLogin(Context context) {

        }

        String getAuthCode();

        void onInAppShare(Context context, String uuid, String iconUrl, String link, String title, String type);
//      void onInAppPay(Context context);

        default void goMyFragemnt(Context context) {

        }

        String getLang();


    }


    public interface LangCallback {//获取语言回调
        void getLang(String lang);
    }

    public static void setLangCallback(LangCallback langListener) {
        TGACallback.langListener = langListener;
    }


    public interface ShareCallback {//分享回调
        void shareCall(String uuid, boolean success);
    }

    public static void setShareCallback(ShareCallback listener) {
        TGACallback.listener = listener;
    }


    public interface FightGameCallback {//对战游戏回调
        void fightGameCall();
    }

    public static void setFightGameCallback(FightGameCallback fightGameListener) {
        TGACallback.fightGameListener = fightGameListener;
    }


    public interface GameCenterCallback{
        void onGameCenterClosed();//关闭h5页回调
        void openUserLogin(String uuid);//调起app登录页
    }


    public interface OutLoginCallback {//退出登录
        void outLoginCall();
    }
    public static void setOutLoginCallback(OutLoginCallback outLoginCallback) {
        TGACallback.outLoginCallback = outLoginCallback;
    }


    public interface CodeCallback {//获取app传过来的code
        void codeCall(String uuid,String code);
    }
    public static void setCodeCallback(CodeCallback codeCallback) {
        TGACallback.codeCallback = codeCallback;
    }

}
