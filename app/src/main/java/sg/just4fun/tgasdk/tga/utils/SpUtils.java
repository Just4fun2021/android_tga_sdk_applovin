package sg.just4fun.tgasdk.tga.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpUtils  {private static SharedPreferences sp;
        public static String mPreferencesName = "share_preference_default";

        /**
         * 设置preferencesName
         *
         * @param preferencesName preferencesName
         */
        private void setPreferencesName(String preferencesName) {
            mPreferencesName = preferencesName;
        }

        /**
         * 写入boolean变量至sp中
         *
         * @param ctx   上下文环境
         * @param key   存储节点名称
         * @param value 存储节点的值
         */
        public static void putBoolean(Context ctx, String key, boolean value) {
            //(存储节点文件名称,读写方式)
            if (sp == null) {
                sp = ctx.getSharedPreferences(mPreferencesName, Context
                        .MODE_PRIVATE);
            }
            sp.edit().putBoolean(key, value).apply();
        }

        /**
         * 读取boolean标示从sp中
         *
         * @param ctx      上下文环境
         * @param key      存储节点名称
         * @param defValue 没有此节点默认值
         * @return 默认值或者此节点读取到的结果
         */
        public static boolean getBoolean(Context ctx, String key, boolean defValue) {
            //(存储节点文件名称,读写方式)
            if (sp == null) {
                sp = ctx.getSharedPreferences(mPreferencesName, Context
                        .MODE_PRIVATE);
            }
            return sp.getBoolean(key, defValue);
        }

        /**
         * 写入String变量至sp中
         *
         * @param ctx   上下文环境
         * @param key   存储节点名称
         * @param value 存储节点的值
         */
        public static void putString(Context ctx, String key, String value) {
            //(存储节点文件名称,读写方式)
            if (sp == null) {
                sp = ctx.getSharedPreferences(mPreferencesName, Context
                        .MODE_PRIVATE);
            }
            sp.edit().putString(key, value).apply();
        }

        /**
         * 读取String标示从sp中
         *
         * @param ctx      上下文环境
         * @param key      存储节点名称
         * @param defValue 没有此节点默认值
         * @return 默认值或者此节点读取到的结果
         */
        public static String getString(Context ctx, String key, String defValue) {
            //(存储节点文件名称,读写方式)
            if (sp == null) {
                sp = ctx.getSharedPreferences(mPreferencesName, Context
                        .MODE_PRIVATE);
            }
            return sp.getString(key, defValue);
        }


        /**
         * 写入int变量至sp中
         *
         * @param ctx   上下文环境
         * @param key   存储节点名称
         * @param value 存储节点的值
         */
        public static void putInt(Context ctx, String key, int value) {
            //(存储节点文件名称,读写方式)
            if (sp == null) {
                sp = ctx.getSharedPreferences(mPreferencesName, Context
                        .MODE_PRIVATE);
            }
            sp.edit().putInt(key, value).apply();
        }

        /**
         * 读取int标示从sp中
         *
         * @param ctx      上下文环境
         * @param key      存储节点名称
         * @param defValue 没有此节点默认值
         * @return 默认值或者此节点读取到的结果
         */
        public static int getInt(Context ctx, String key, int defValue) {
            //(存储节点文件名称,读写方式)
            if (sp == null) {
                sp = ctx.getSharedPreferences(mPreferencesName, Context
                        .MODE_PRIVATE);
            }
            return sp.getInt(key, defValue);
        }


        /**
         * 从sp中移除指定节点
         *
         * @param ctx 上下文环境
         * @param key 需要移除节点的名称
         */
        public static void remove(Context ctx, String key) {
            if (sp == null) {
                sp = ctx.getSharedPreferences(mPreferencesName, Context
                        .MODE_PRIVATE);
            }
            sp.edit().remove(key).apply();
        }

        /**
         * 保存List
         *
         * @param key      sp key值
         * @param datalist list
         * @param <T>      item 类型
         */
        public static <T> void setDataList(String key, List<T> datalist) {
            if (null == datalist || datalist.size() <= 0) {
                return;
            }

            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(datalist);
            SpUtils.putString(AppUtils.getContext(), key, strJson);
        }

        /**
         * 获取List
         *
         * @param key sp key值
         * @param <T> item 类型
         * @return list
         */
        public static <T> List<T> getDataList(String key, Class<T> cls) {
            List<T> datalist = new ArrayList<T>();
            String strJson = SpUtils.getString(AppUtils.getContext(), key, null);

            if (null == strJson) {
                return datalist;
            }

            try {
                Gson gson = new Gson();
                //        datalist = gson.fromJson(strJson, new TypeToken<List<T>>(){}.getType());
                JsonArray array = new JsonParser().parse(strJson).getAsJsonArray();
                for (final JsonElement elem : array) {
                    datalist.add(gson.fromJson(elem, cls));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return datalist;
        }

        public static <T> List<T> getDataList(String key) {
            List<T> datalist = new ArrayList<T>();
            String strJson = SpUtils.getString(AppUtils.getContext(), key, null);
            if (null == strJson) {
                return datalist;
            }
            Gson gson = new Gson();
            datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
            }.getType());
            try {
                Collections.reverse(datalist);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return datalist;

        }

        /**
         * date: 2018/3/15 下午12:00
         *
         * @MethodName: 清理退出
         */

        public static void clean(Context context) {
            SharedPreferences sp = context.getSharedPreferences(mPreferencesName, Activity.MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.clear();
            edit.commit();
        }

        public static boolean getIsLogin(Context mContext, boolean is) {
            SharedPreferences preferences = mContext.getSharedPreferences("isfirst",
                    Context.MODE_PRIVATE);
            boolean wallet = preferences.getBoolean("isfirst", is);
            return wallet;
        }

        public static void putIsLogin(Context mContext, boolean is) {
            SharedPreferences preferences = mContext.getSharedPreferences("isfirst",
                    Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("isfirst", is);
            edit.commit();

        }


        /*---------------------------------------------首页通知------------------------------------------------------*/

        //首页通知
        public static void saveNoticeUnLogin(Context mContext, String notice_id) {
            SharedPreferences preferences = mContext.getSharedPreferences("is_notice", 0);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("is_notice", notice_id);
            edit.commit();
        }

        //首页通知
        public static String getNoticeUnLogin(Context mContext) {
            SharedPreferences preferences = mContext.getSharedPreferences("is_notice", 0);
            String isShow = preferences.getString("is_notice", "");
            return isShow;
        }


        /*---------------------------------------------实名认证单号------------------------------------------------------*/

        //实名认证单号
        public static void saveAuthNode(Context mContext, String node) {
            SharedPreferences preferences = mContext.getSharedPreferences("auth_node", 0);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("auth_node", node);
            edit.commit();
        }

        //实名认证单号
        public static String getAuthNode(Context mContext) {
            SharedPreferences preferences = mContext.getSharedPreferences("auth_node", 0);
            String isShow = preferences.getString("auth_node", "");
            return isShow;
        }



        /**
         * 搜索界面搜索城市历史的数据操作 保存
         *
         * @param context
         * @param city
         */
        public static void saveHistoricalRecords(Context context, String city) {
            SharedPreferences preferences = context.getSharedPreferences("searchcity", 0);
            SharedPreferences.Editor edit = preferences.edit();
            StringBuffer sb = new StringBuffer();
            ArrayList<String> arrayList = readHistoricalRecords(context);
            for (int i = 0; i < arrayList.size(); i++) {
                if (!city.equals(arrayList.get(i))) {
                    sb.append(arrayList.get(i) + "&");
                }
            }
            edit.putString("citys", city + "&" + sb.toString());
            edit.commit();
        }

        /**
         * 读取
         *
         * @param context
         * @return
         */
        public static ArrayList<String> readHistoricalRecords(Context context) {
            ArrayList<String> citys = new ArrayList<String>();
            SharedPreferences sharedPreferences = context.getSharedPreferences("searchcity", 0);
            String string = sharedPreferences.getString("citys", "");
            String[] split = string.split("&");
            for (int i = 0; i < split.length; i++) {
                if (!split[i].equals("")) {
                    citys.add(split[i]);
                }
            }
            return citys;
        }

        /**
         * 清空
         *
         * @param context
         */
        public static void cleanSearch(Context context) {
            SharedPreferences preferences = context.getSharedPreferences("searchcity", 0);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putString("citys", null);
            edit.commit();
        }
}