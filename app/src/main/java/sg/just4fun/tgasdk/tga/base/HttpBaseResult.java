package sg.just4fun.tgasdk.tga.base;

public class HttpBaseResult <T> {

    private int stateCode;
    private T resultInfo;

    public int getStateCode() {
        return stateCode;
    }

    public void setStateCode(int stateCode) {
        this.stateCode = stateCode;
    }


    public T getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(T resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Override
    public String toString() {
        return "{" +
                "stateCode=" + stateCode +
                ", resultInfo=" + resultInfo +
                '}';
    }
}