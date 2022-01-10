package sg.just4fun.tgasdk.modle;

public class GameinfoBean{
    private String gameId;//":"游戏ID",
    private String gameCategoryId;//":"游戏归属",
    private String name;//":"游戏名称",
    private String imageSrc;//":"游戏图标",
    private String playUrl;//":"游戏地址",
    private String backUrl;//":"游戏背景颜色",
    private String remark;//":"游戏说明",
    private String totalPlayer;//":"总游戏人数",
    private String gameType;//":"游戏类型",
    private String isVIP;//":"是否是VIP专属游戏",
    private String isNew;//":"是否是新游戏",
    private String isHot;//":"是否是热门游戏"

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameCategoryId() {
        return gameCategoryId;
    }

    public void setGameCategoryId(String gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTotalPlayer() {
        return totalPlayer;
    }

    public void setTotalPlayer(String totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getIsVIP() {
        return isVIP;
    }

    public void setIsVIP(String isVIP) {
        this.isVIP = isVIP;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }
}
