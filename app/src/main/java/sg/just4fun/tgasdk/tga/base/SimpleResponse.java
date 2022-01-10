package sg.just4fun.tgasdk.tga.base;


import java.io.Serializable;

public class SimpleResponse implements Serializable {

    private int stateCode;
    private int error_code;
    private String message;

    public int getStatusCode() {
        return stateCode;
    }

    public void setStatusCode(int stateCode) {
        this.stateCode = stateCode;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpBaseResult toLzyResponse() {
     HttpBaseResult lzyResponse = new HttpBaseResult();
//        lzyResponse.setError_code(error_code);
        lzyResponse.setStateCode(stateCode);
//        lzyResponse.setMessage(message);
        return lzyResponse;
    }
}
