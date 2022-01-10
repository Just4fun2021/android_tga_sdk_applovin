package sg.just4fun.tgasdk.modle;

import org.json.JSONObject;

import sg.just4fun.tgasdk.adsdk.TgaSdkJsonEntity;

public  class PriceBean  implements TgaSdkJsonEntity {
    private String cnt;
    private String ratio;
    private String  unit;

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cnt", cnt);
        jsonObject.put("ratio",ratio);
        jsonObject.put("unit",unit);
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {

    }

    public PriceBean() {
    }

    public PriceBean(String cnt, String ratio, String unit) {
        this.cnt = cnt;
        this.ratio = ratio;
        this.unit = unit;
    }
}
