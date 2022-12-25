package uz.app.YurAI.security.model;

import uz.app.Anno.orm.IEntity;
import uz.app.Anno.orm.annotations.Column;
import uz.app.Anno.orm.annotations.Id;
import uz.app.Anno.orm.annotations.Generated;
import uz.app.Anno.orm.annotations.Schema;
import uz.app.Anno.orm.annotations.Table;
import uz.app.Anno.orm.exceptions.ValidationException;

@Schema("public")
@Table("Users")
public class User implements IUser, IEntity{
    @Id
    @Generated
    @Column("id")
    private Long id;
    @Column("login")
    private String login;
    @Column("full_name")
    private String fullName;
    @Column("email")
    private String email;
    @Column("profile_pic")
    private String profilePicUrl;
    @Column("password")
    private String password;

    public User() {
        this.id = null;
        this.login = null;
    }

    public User(long id, String login, String fullName, String email, String profilePicUrl, String password) {
        this.id = id;
        this.login = login;
        this.fullName = fullName;
        this.email = email;
        this.profilePicUrl = profilePicUrl;
        this.password = password;
    } 

    @Override
    public long getId() {
        if(this.id == null)
            throw new RuntimeException("Id is not set!");
        return this.id;
    }

    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public String getLogin() {
        if(this.login == null)
            throw new RuntimeException("Login is not set!");
        return this.login;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    public void setId(Number id) {
        this.id = id.longValue();
    }

    @Override
    public String getProfilePictureUrl() {
        return this.profilePicUrl;
    }

    @Override
    public void setFullName(String name) {
        this.fullName = name;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setProfilePicUrl(String profilePic) {
        this.profilePicUrl = profilePic;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void validate() throws ValidationException {
        
    }
}
