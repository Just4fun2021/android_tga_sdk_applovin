package sg.just4fun.tgasdk.adsdk;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TgaJsonSample implements TgaSdkJsonEntity {

    private int age;
    private String name;
    private boolean good;
    private Date time;
    private long ms;
    private double rate;
    private String[] opts;
    private List<Integer> cells;

    public TgaJsonSample() {
    }

    public TgaJsonSample(int age, String name, boolean good, Date time, long ms, double rate, String[] opts, List<Integer> cells) {
        this.age = age;
        this.name = name;
        this.good = good;
        this.time = time;
        this.ms = ms;
        this.rate = rate;
        this.opts = opts;
        this.cells = cells;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getMs() {
        return ms;
    }

    public void setMs(long ms) {
        this.ms = ms;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String[] getOpts() {
        return opts;
    }

    public void setOpts(String[] opts) {
        this.opts = opts;
    }

    public List<Integer> getCells() {
        return cells;
    }

    public void setCells(List<Integer> cells) {
        this.cells = cells;
    }

    @Override
    public JSONObject toJson() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("age",TgaAdSdkUtils.jsonValueOf(this.age));
        jsonObject.put("name",TgaAdSdkUtils.jsonValueOf(this.name));
        jsonObject.put("good",TgaAdSdkUtils.jsonValueOf(this.good));
        jsonObject.put("time",TgaAdSdkUtils.jsonValueOf(this.time));
        jsonObject.put("ms",TgaAdSdkUtils.jsonValueOf(this.ms));
        jsonObject.put("rate",TgaAdSdkUtils.jsonValueOf(this.rate));
        jsonObject.put("opts",TgaAdSdkUtils.jsonValueOf(this.opts));
        jsonObject.put("cells",TgaAdSdkUtils.jsonValueOf(this.cells));
        return jsonObject;
    }

    @Override
    public void fromJson(JSONObject jsonObject) throws Exception {

        this.age = jsonObject.getInt("age");
        this.name = jsonObject.getString("name");
        this.good = jsonObject.getBoolean("good");
        this.time = TgaAdSdkUtils.toDate(jsonObject, "time");
        this.ms = jsonObject.getLong("ms");
        this.rate = jsonObject.getDouble("rate");
        JSONArray jsonArray = jsonObject.isNull("opts") ? null : jsonObject.getJSONArray("opts");
        if(jsonArray == null) {
            this.opts = null;
        }
        this.opts = new String[jsonArray.length()];
        for(int i = 0 ; i < opts.length; i++) {
            opts[i] = jsonArray.getString(i);
        }

        jsonArray = jsonObject.isNull("cells") ? null : jsonObject.getJSONArray("cells");
        if(jsonArray == null) {
            this.cells = null;
        }
        this.cells = new ArrayList<>();
        for(int i = 0 ; i < jsonArray.length(); i++) {
            cells.add(jsonArray.getInt(i));
        }
    }

}
