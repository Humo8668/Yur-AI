package uz.app.YurAI.security.model;

public interface IUser {
    public long getId();
    public String getFullName();
    public String getLogin();
    public String getEmail();
    public String getProfilePictureUrl();
    public String getPassword();

    public void setFullName(String name);
    public void setEmail(String email);
    public void setProfilePicUrl(String profilePic);
    public void setPassword(String password);
}
