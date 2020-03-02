package com.example.collaborativeapp.Constructor;

public class UserProfile {
    private String EmailAddress;
    private String Password;
    private String Username ;
//   public UserProfile(){
//
//    }

    public UserProfile(String EmailAddress,String Password , String Username) {
        this.EmailAddress = EmailAddress;
        this.Password = Password;
        this.Username = Username;

    }

    public String getEmailAddress() {
        return EmailAddress;
    }
    public String getUsername() {
        return Username;
    }
    public String getPassword(){return Password;}
}
