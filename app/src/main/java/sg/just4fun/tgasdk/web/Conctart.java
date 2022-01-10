package sg.just4fun.tgasdk.web;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class Conctart {
    public static String toStdLocale(String orgLocale) {
        return orgLocale.toLowerCase().replace(" ", "").replace("_", "-").replace("#", "").trim();
    }

    public static String toStdLang(String locale) {
        if(locale == null) {
            return null;
        }
        String tLocale = toStdLocale(locale);
        //特殊规则
        String[] zhLocales = {"zh","cn","zh-cn","zh-sg","zh-hans","zh-hans-cn","zh-cn-hans"};
        String[] twLocales = {"tw","hk","zh-tw","zh-hk","mo","zh-mo"}; //要添加繁体中文的案例在这里
        Set<String> zhLocaleSet = new HashSet<>();
        for(String zhl:zhLocales) {
            zhLocaleSet.add(zhl);
        }
        Set<String> twLocaleSet = new HashSet<>();
        for(String twl:twLocales) {
            twLocaleSet.add(twl);
        }
        if(zhLocaleSet.contains(tLocale)) {
            return "zh";
        }
        if(twLocaleSet.contains(tLocale)) {
            return "tw";
        }

        //一般规则
        return tLocale.split("-")[0];
    }



    /**
     * 根据key从Application中返回的Bundle中获取value
     *
     * @param key
     * @param defValue
     * @return
     */
    public static String getMetaDataStringApplication(Activity context, String key, String defValue) {
        Bundle bundle = getAppMetaDataBundle(context.getPackageManager(), context.getPackageName());
        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getString(key);
        }
        return defValue;
    }

    /**
     * 获取Application中的meta-data.
     *
     * @param packageManager
     * @param packageName
     * @return
     */
    private static Bundle getAppMetaDataBundle(PackageManager packageManager,
                                               String packageName) {
        Bundle bundle = null;
        try {
            ApplicationInfo ai = packageManager.getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA);
            bundle = ai.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getMetaDataBundle", e.getMessage(), e);
        }
        return bundle;
    }

    /**
     * 根据key从Activity中返回的Bundle中获取value
     *
     * @param key
     * @param defValue
     * @return
     */
//    private String getMetaDataStringFromActivity(String key, String defValue) {
//        Bundle bundle = getActivityMetaDataBundle(context.getPackageManager(), context.getComponentName());
//        if (bundle != null && bundle.containsKey(key)) {
//            return bundle.getString(key);
//        }
//        return defValue;
//    }

    /**
     * 获取Activity中的meta-data.
     *
     * @param packageManager
     * @param component
     * @return
     */
    private Bundle getActivityMetaDataBundle(PackageManager packageManager, ComponentName component) {
        Bundle bundle = null;
        try {
            ActivityInfo ai = packageManager.getActivityInfo(component,
                    PackageManager.GET_META_DATA);
            bundle = ai.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("getMetaDataBundle", e.getMessage(), e);
        }

        return bundle;
    }
}
