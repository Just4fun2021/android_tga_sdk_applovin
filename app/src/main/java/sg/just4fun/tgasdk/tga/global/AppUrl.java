package sg.just4fun.tgasdk.tga.global;

public class AppUrl {

    public static final String TGA_SDK_INFO=Global.TEST_HOST_GOOGLE_PAY_URL_NEW+"tgampApp/AppNativeInit";//获取用户配置表
    public static final String GET_GOOGLEPAY_INFO=Global.TEST_HOST_GOOGLE_PAY_URL_NEW+"googlePay/GetWareList";//获取googlepay配置表
    public static final String GET_GOOGLEPAY_RESULT=Global.TEST_HOST_GOOGLE_PAY_URL_NEW+"googlePay/PayFinish";//googlepay支付结果
    public static final String GET_GAME_LIST=Global.TEST_HOST_GOOGLE_PAY_URL_NEW+"tgadpGame/GameListByTag";//获取对战优秀列表
    public static final String GAME_BIP_LOGIN_SDK=Global.TEST_HOST_GOOGLE_PAY_URL_NEW+"tgadpUser/TPLogin";//bip登录获取用户信息
    public static final String GAME_BIP_CODE_SDK_USER_INFO=Global.TEST_HOST_GOOGLE_PAY_URL_NEW+"tgadpUser/TPLoginByCode";//bip登录获取用户信息
    public static final String GAME_REFRESHTOKEN=Global.TEST_HOST_GOOGLE_PAY_URL_NEW+"tgadpUser/RefreshToken";//获取刷新后的token
    public static final String GET_USER_CODE=Global.TEST_HOST_GOOGLE_PAY_URL_NEW+"tgadpUser/CreateLoginCode";//获取通过用户信息获取相对于code

    public static final String BIP_TGA_SDK_INFO=Global.BIP_TEST_HOST_GOOGLE_PAY_URL_NEW+"tgampApp/AppNativeInit";//获取用户配置表
    public static final String BIP_GET_GOOGLEPAY_INFO=Global.BIP_TEST_HOST_GOOGLE_PAY_URL_NEW+"googlePay/GetWareList";//获取googlepay配置表
    public static final String BIP_GET_GAME_LIST=Global.BIP_TEST_HOST_GOOGLE_PAY_URL_NEW+"tgadpGame/GameListByTag";//获取对战优秀列表
    public static final String BIP_GAME_BIP_LOGIN_SDK=Global.BIP_TEST_HOST_GOOGLE_PAY_URL_NEW+"tgadpUser/TPLogin";//bip登录获取用户信息
    public static final String BIP_GAME_BIP_CODE_SDK_USER_INFO=Global.BIP_TEST_HOST_GOOGLE_PAY_URL_NEW+"tgadpUser/TPLoginByCode";//bip登录获取用户信息


}
