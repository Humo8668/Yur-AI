package uz.app.YurAI.documents.model;


import java.util.Date;

import uz.app.Anno.orm.IEntity;
import uz.app.Anno.orm.annotations.*;
import uz.app.Anno.orm.exceptions.ValidationException;

@Schema("public")
@Table("Documents")
public class Document implements IEntity {
    @Id
    @Generated()
    @Column("id")
    private long id;
    @Column("file_name")
    private String documentName;
    @Column("file_full_url")
    private String documentUrl;
    @Column("base64_source")
    private String source; // in base64 encoded format
    @Column("uploaded_by")
    private long uploaded_by;
    @Generated
    @Column("uploaded_at")
    private Date uploaded_at;

    public Document() {};

    public Document(String name, String source) {
        this.documentName = name;
        this.source = source;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getDocumentName() {
        return documentName;
    }
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getDocumentUrl() {
        return documentUrl;
    }
    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }
    
    public long getUploaded_by() {
        return uploaded_by;
    }

    public void setUploaded_by(long uploaded_by) {
        this.uploaded_by = uploaded_by;
    }

    public Date getUploaded_at() {
        return uploaded_at;
    }

    public void setUploaded_at(Date uploaded_at) {
        this.uploaded_at = uploaded_at;
    }
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {
        
    }
}
