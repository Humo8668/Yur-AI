package uz.app.YurAI.common.model;

public class StdResponse {
    private String code;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    private String msg;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public StdResponse() {}

    public StdResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
