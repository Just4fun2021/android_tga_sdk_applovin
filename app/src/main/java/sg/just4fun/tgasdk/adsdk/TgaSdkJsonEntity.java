package sg.just4fun.tgasdk.adsdk;

import org.json.JSONObject;

public interface TgaSdkJsonEntity {

    JSONObject toJson() throws Exception;
    void fromJson(JSONObject jsonObject) throws Exception;


}
