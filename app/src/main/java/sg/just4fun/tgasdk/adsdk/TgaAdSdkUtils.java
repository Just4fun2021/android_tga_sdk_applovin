package sg.just4fun.tgasdk.adsdk;

import android.os.Build;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

//import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TgaAdSdkUtils {

    public static String tgaUrl;

//    public static ShareDialog shareDialog;

    public static void registerTgaWebview(WebView newWebview) {
//        tgaWebview = newWebview;
    }

    private static Map<String, LinkedList<String>> caches = new ConcurrentHashMap<>();

    public static Object jsonValueOf(Object object) throws TgaSdkException {
        try {
            if (object == null) {
                return null;
            }
            if (object.getClass().isArray()) {
                int arrayLength = Array.getLength(object);
                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < arrayLength; i++) {
                    jsonArray.put(i, jsonValueOf(Array.get(object, i)));
                }
                return jsonArray;
            }
            if (object instanceof String || object instanceof Boolean || object instanceof Integer || object instanceof Long || object instanceof Double || object instanceof Float || object instanceof JSONObject || object instanceof JSONArray) {
                return object;
            }
            if (object instanceof Date) {
                return ((Date) object).getTime();
            }
            if (object instanceof TgaSdkJsonEntity) {
                return ((TgaSdkJsonEntity) object).toJson();
            }
            if (object instanceof Iterable) {
                return toJSONArray((Iterable) object);
            }
            if (object instanceof Map) {
                return new JSONObject((Map) object);
            }
            return object;
        } catch (Exception e) {
            throw new TgaSdkException(e);
        }
    }


    public static JSONArray toJSONArray(Iterable iter) throws TgaSdkException {
        if (iter == null) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        int index = 0;
        for (Object ele : iter) {
            try {
                jsonArray.put(index++, jsonValueOf(ele));
            } catch (Exception e) {
                throw new TgaSdkException("Json Resolve Failed", e);
            }
        }
        return jsonArray;
    }


    public static String dataToValue(Object data) throws TgaSdkException {
        if(data == null) {
            return null;
        } else {
            return jsonOf(data);
        }
    }

    public static String jsonOf(Object object) throws TgaSdkException {
        try {
            Object jsonValue = jsonValueOf(object);
            if (jsonValue == null) {
                return "null";
            }
            if (jsonValue instanceof JSONObject) {
                return ((JSONObject) jsonValue).toString();
            }
            if (jsonValue instanceof JSONArray) {
                return ((JSONArray) jsonValue).toString();
            }
            if (jsonValue instanceof String) {
                JSONArray stringShell = new JSONArray();
                stringShell.put(0, (String) jsonValue);
                String shellJson = stringShell.toString().trim();
                return shellJson.substring(1, shellJson.length() - 1).trim();
            }
            return "" + jsonValue;
        } catch (Exception e) {
            throw new TgaSdkException(e);
        }
    }

    public static JSONObject objectFromJson(String jsonStr) throws JSONException {
        return new JSONObject(jsonStr);
    }

    public static JSONArray arrayFromJson(String jsonStr) throws JSONException {
        return new JSONArray(jsonStr);
    }


    public static Date toDate(JSONObject jsonObject, String key) throws TgaSdkException {
        if (jsonObject.isNull(key)) {
            return null;
        }
        try {
            long longValue = jsonObject.getLong(key);
            return new Date(longValue);
        } catch (Exception e) {

        }
        try {
            int intValue = jsonObject.getInt(key);
            return new Date(intValue);
        } catch (Exception e) {
            throw new TgaSdkException("Json Resolve Failed", e);
        }

    }

    public static Date tryToDate(JSONObject jsonObject, String key) {
        return tryToDate(jsonObject, key, null);
    }

    public static Date tryToDate(JSONObject jsonObject, String key, Date valIfFailed) {
        Date result = null;
        try{
            result = toDate(jsonObject, key);
        } catch(Exception e) {
        }
        return result == null ? valIfFailed : result;
    }

    public static String appendParameterToUrl(String url, String key, String value) throws Exception {
        int whIdx = url.indexOf("?");
        if (whIdx > 6) {
            return url + "&" + URLEncoder.encode(key, "utf-8") + "=" + URLEncoder.encode(value, "utf-8");
        } else {
            return url + "?" + URLEncoder.encode(key, "utf-8") + "=" + URLEncoder.encode(value, "utf-8");
        }
    }




    public static String toTgaSdkOnEventJsCode(TgaEventBaseInfo eventInfo, Object data) throws Exception {
        Object ext = data == null ? null : jsonValueOf(data);
        TgaAdSdkEventData nData = new TgaAdSdkEventData(ext, eventInfo.getMethod(), eventInfo.getModel());

        TgaSdkEventDataBean eventData = new TgaSdkEventDataBean(eventInfo.getUuid(), nData);
        return "TgaSdk.onEvent(null, "+ eventData.toJsCode()  +")";
    }

    public static void runEvent(WebView webView, TgaEventBaseInfo eventInfo, Object data, String logTag) throws Exception {
        runEvent(webView, eventInfo, data, logTag, true);
    }

    public static void runEvent(WebView webView, TgaEventBaseInfo eventInfo, Object data, String logTag, boolean doCache) throws Exception {
        if(eventInfo == null) {
            return;
        }

//        if("onAdLoad".equals(eventInfo.getMethod()) || !doCache) {
        if("onAdLoad".equals(eventInfo.getMethod()) || !doCache) {
//            String scriptCode = data == null ? "TgaSdk.onEvent("+eventInfo.toJson().toString()+")" : "TgaSdk.onEvent("+eventInfo.toJson().toString()+", "+ jsonOf(data) +")";
            String scriptCode = toTgaSdkOnEventJsCode(eventInfo, data);
            Log.d(logTag, "RUNEVENT " + scriptCode);
            try{
                webView.post( new ScriptCodeRunnable(scriptCode, webView));
                return;
            } catch(Exception e) {

            }
        }
        String cache = toTgaSdkOnEventJsCode(eventInfo, data);
        if(caches.keySet().size() > 1000) {
            caches.clear();
        }
        LinkedList<String> singleBean = new LinkedList<>();
        singleBean.add(cache);
        if(!caches.containsKey(eventInfo.getUuid())) {
            caches.put(eventInfo.getUuid(), singleBean);
        } else {
            LinkedList<String> cachesForThisUUid = caches.get(eventInfo.getUuid());
            if(cachesForThisUUid.size() > 255) {
                cachesForThisUUid.removeFirst();
            }
            cachesForThisUUid.add(cache);
        }
        Log.d(logTag, "add event cache " + cache);


    }

//    public static String toJavaScriptCode(TgaEventBaseInfo data, String extjson) throws Exception {
//        return  extjson == null ? "TgaSdk.onEvent("+data.toJson().toString()+")" : "TgaSdk.onEvent("+data.toJson().toString()+", "+ extjson +")";
//    }
public static class ScriptCodeRunnable implements Runnable {
    private final String scriptCode;
    private final WebView webView;
    public ScriptCodeRunnable(String scriptCode, WebView webView) {
        this.scriptCode = scriptCode;
        this.webView = webView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void run() {
        Log.d("执行了回调", "JSON="+scriptCode +" run on " +
                webView.getUrl());
        webView.evaluateJavascript(scriptCode, null);
    }
}

    public static void runEventForUnsupportedType(WebView webView, String model, String uuid, String targetTypeName, String logTag) throws Exception {
        JSONObject errorInfo = new JSONObject();
        errorInfo.put("requiredType", targetTypeName);
        errorInfo.put("message", "Unsupported Ad Type");
        runEvent(
            webView,
            new TgaEventBaseInfo(
                    uuid,
                    "onError",
                    model
            ),
            errorInfo,
            logTag
        );
    }

    public static String checkEvent(String uuid) {
        if(caches.containsKey(uuid)) {
            List<String> events = caches.get(uuid);
            JSONArray jsonArray = new JSONArray();
            int index = 0;
            for(String event:events) {
                try{
                    jsonArray.put(index++, event);
                } catch (Exception e) {

                }
            }
            return jsonArray.toString();
        } else {
            return "[]";
        }
    }

    public static void releaseEvent(String uuid) {
        if(caches.containsKey(uuid)) {
            caches.remove(uuid);
        }
    }

    public static void clearCaches() {
        caches = new ConcurrentHashMap<>();
    }

    public static void flushAllEvents() {
//        flushAllEvents(tgaWebview);
    }
    public static void flushAllEvents(final WebView tgaWebview) {
        flushAllEvents(tgaWebview, "tgaad");
    }
    public static void flushAllEvents(final WebView tgaWebview, String tag) {
        Log.d(tag, "flushAllEvents with " + caches);
        for(String uuid:caches.keySet()) {
            try{
                List<String> cache = caches.remove(uuid);
                for(String scriptCode:cache) {
                    tgaWebview.post(new ScriptCodeRunnable(scriptCode, tgaWebview));
                }
            } catch(Exception e) {

            }
        }
    }



}


