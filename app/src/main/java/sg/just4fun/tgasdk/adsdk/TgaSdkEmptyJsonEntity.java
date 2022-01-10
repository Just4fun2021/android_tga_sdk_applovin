package sg.just4fun.tgasdk.adsdk;

import org.json.JSONObject;

public class TgaSdkEmptyJsonEntity implements TgaSdkJsonEntity{

    public TgaSdkEmptyJsonEntity() {
    }

    @Override
    public JSONObject toJson() throws Exception {
        return new JSONObject();
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {

    }
}
