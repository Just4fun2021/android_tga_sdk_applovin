package sg.just4fun.tgasdk.adsdk;

public enum TgaAdType {
    FullScreen("fullscreen"),
    NoSkip("noskip"),
    Title("title"),

    Banner("banner"),
    Reward("reward"),
    RewardNoSkip("rewardnoskip"),
    Other("other");

    private String name;

    TgaAdType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static TgaAdType toTgaAdType(String name) {
        if(name == null) {
            return TgaAdType.Other;
        }
        for(TgaAdType value:TgaAdType.values()) {
            if(name.trim().toLowerCase().equals(value.getName().toLowerCase().trim())) {
                return value;
            }
        }
        return TgaAdType.Other;
    }
}
