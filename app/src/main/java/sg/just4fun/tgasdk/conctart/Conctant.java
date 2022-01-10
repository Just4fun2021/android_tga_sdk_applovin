package sg.just4fun.tgasdk.conctart;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import sg.just4fun.tgasdk.modle.GameNameDataTitle;

public class Conctant {

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无法获取到版本号";
        }
    }

    public static boolean isBadJson(String json) {
        JsonElement jsonElement;
        try {
            jsonElement = new JsonParser().parse(json);
        } catch (Exception e) {
            return false;
        }
        if (jsonElement == null) {
            return false;
        }
        if (!jsonElement.isJsonObject()) {
            return false;
        }
        return true;
    }


    public static String gameName(String lang,String name){
        ArrayList<String> langlist = new ArrayList<>();
        String dxLang = lang.toUpperCase();
        Gson gson = new Gson();
        boolean badJson = isBadJson(name);
        if (badJson){
            return name;
        }else {
            Object json = null;
            try {
                json = new JSONTokener(name).nextValue();
                if (json instanceof JSONObject) {
                    String values = ((JSONObject) json).getString("values");
                    return values;
                } else if (json instanceof JSONArray) {
                    JSONArray jsonArray = new JSONArray(name);
                    for (int a=0;a<jsonArray.length();a++){
                        String jsonObject = String.valueOf(jsonArray.getJSONObject(a));
                        GameNameDataTitle bipGameFristDataTitle = gson.fromJson(jsonObject, GameNameDataTitle.class);
                        if(dxLang.equals(bipGameFristDataTitle.getLang())){
                            return bipGameFristDataTitle.getValues();
                        }else {
                            langlist.add(bipGameFristDataTitle.getLang());
                        }

                    }
                    if (!langlist.contains(dxLang)){
                        if (langlist.contains("EN")){
                            for (int c=0;c<langlist.size();c++){
                                if (langlist.get(c).equals("EN")) {
                                    String jsonObject = String.valueOf(jsonArray.getJSONObject(c));
                                    GameNameDataTitle bipGameFristDataTitle = gson.fromJson(jsonObject,GameNameDataTitle.class);
                                    return bipGameFristDataTitle.getValues();
                                }
                            }

                        }else {
                            String jsonObject = String.valueOf(jsonArray.getJSONObject(0));
                            GameNameDataTitle bipGameFristDataTitle = gson.fromJson(jsonObject, GameNameDataTitle.class);
                            return bipGameFristDataTitle.getValues();
                        }
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }

        return "";


    }


}
