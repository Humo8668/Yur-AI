package uz.app.YurAI.documents.model;

public class ProcessResultResponse {
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
    private String verdict;
    public String getVerdict() {
        return verdict;
    }
    public void setVerdict(String verdict) {
        this.verdict = verdict;
    }

    public ProcessResultResponse() {}

    public ProcessResultResponse(String code, String msg, String verdict) {
        this.code = code;
        this.msg = msg;
        this.verdict = verdict;
    }
}
