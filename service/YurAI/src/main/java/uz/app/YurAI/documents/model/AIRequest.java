package uz.app.YurAI.documents.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.Gson;

import uz.app.Anno.orm.IEntity;
import uz.app.Anno.orm.annotations.Column;
import uz.app.Anno.orm.annotations.Generated;
import uz.app.Anno.orm.annotations.Id;
import uz.app.Anno.orm.annotations.Schema;
import uz.app.Anno.orm.annotations.Table;
import uz.app.Anno.orm.exceptions.ValidationException;

@Schema("public")
@Table("ai_requests")
public class AIRequest implements IEntity {
    @Id
    @Generated
    @Column("request_id")
    public long request_id;
    @Column("base64_file")
    public String base64_file;
    @Column("request_time")
    @JsonIgnore
    private Date requestTime;

    public AIRequest(){}

    public AIRequest(String base64_file) {
        this.base64_file = base64_file;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public String getBase64_file() {
        return base64_file;
    }

    public void setBase64_file(String base64_file) {
        this.base64_file = base64_file;
    }
    
    public long getRequest_id() {
        return request_id;
    }

    public void setRequest_id(long request_id) {
        this.request_id = request_id;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
