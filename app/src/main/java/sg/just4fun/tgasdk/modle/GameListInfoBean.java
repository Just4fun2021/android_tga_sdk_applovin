package sg.just4fun.tgasdk.modle;

import java.util.List;

public class GameListInfoBean {
//  "desc" :"SUCCESS",
//          "data" : [{
//        "gameId":"游戏ID",
//                "gameCategoryId":"游戏归属",
//                "name":"游戏名称",
//                "imageSrc":"游戏图标",
//                "playUrl":"游戏地址",
//                "backUrl":"游戏背景颜色",
//                "remark":"游戏说明",
//                "totalPlayer":"总游戏人数",
//                "gameType":"游戏类型",
//                "isVIP":"是否是VIP专属游戏",
//                "isNew":"是否是新游戏",
//                "isHot":"是否是热门游戏"
//    },{
//                "gameId":"游戏ID",
//                "gameCategoryId":"游戏归属",
//                "name":"游戏名称",
//                "imageSrc":"游戏图标",
//                "playUrl":"游戏地址",
//                "backUrl":"游戏背景颜色",
//                "remark":"游戏说明",
//                "totalPlayer":"总游戏人数",
//                "gameType":"游戏类型",
//                "isVIP":"是否是VIP专属游戏",
//                "isNew":"是否是新游戏",
//                "isHot":"是否是热门游戏"
//    },
//            ...
//            ],
//            "totalCount" : 所有符合该条件的数据条数,
//            "itemCount" : 当前拉取结果长度,


    private String desc;
    private List<GameinfoBean>data;
    private int totalCount;//" : 所有符合该条件的数据条数,
    private int itemCount;//" : 当前拉取结果长度,

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<GameinfoBean> getData() {
        return data;
    }

    public void setData(List<GameinfoBean> data) {
        this.data = data;
    }



}
