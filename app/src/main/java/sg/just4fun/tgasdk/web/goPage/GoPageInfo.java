package sg.just4fun.tgasdk.web.goPage;

import org.json.JSONObject;

import java.util.LinkedList;

import sg.just4fun.tgasdk.adsdk.TgaAdSdkUtils;
import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;

public class GoPageInfo implements TgaSdkJsonEntity {
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LinkedList<String> getInfo() {
        return info;
    }

    public void setInfo(LinkedList<String> info) {
        this.info = info;
    }

    public GoPageInfo(String uuid, LinkedList<String> info) {
        this.uuid = uuid;
        this.info = info;
    }
    public GoPageInfo(String uuid,String data) {
        this.uuid = uuid;
        this.data = data;

    }
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    private String  uuid;
    private String  data;
    private LinkedList<String> info;
    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", TgaAdSdkUtils.jsonValueOf(this.uuid));
        jsonObject.put("data",TgaAdSdkUtils.jsonValueOf(this.data));
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {

    }



}
