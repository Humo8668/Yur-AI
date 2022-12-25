package uz.app.YurAI.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class SecurityUser extends org.springframework.security.core.userdetails.User implements IUser {
    private long id;
    private String fullName;
    private String email;
    private String profilePicUrl;

    public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        //TODO Auto-generated constructor stub
    }

    public SecurityUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    
    @Override
    public long getId() {
        return this.id;
    }
    @Override
    public String getFullName() {
        return this.fullName;
    }

    @Override
    public String getLogin() {
        return this.getUsername();
    }

    @Override
    public String getEmail() {
        return this.email;
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
    public void setPassword(String password) {
        return;
    }
}
