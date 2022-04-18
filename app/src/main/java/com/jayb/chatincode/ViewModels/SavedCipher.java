package com.jayb.chatincode.ViewModels;

import java.util.Date;

public class SavedCipher {
    private String SaveName;
    private String Cipher;
    private String EncryptMethod;
    private String User;
    private Date DateCreated;

    public SavedCipher() {
    }

    public String getSaveName() {
        return SaveName;
    }

    public void setSaveName(String saveName) {
        SaveName = saveName;
    }

    public String getCipher() {
        return Cipher;
    }

    public void setCipher(String cipher) {
        Cipher = cipher;
    }

    public String getEncryptMethod() {
        return EncryptMethod;
    }

    public void setEncryptMethod(String encryptMethod) {
        EncryptMethod = encryptMethod;
    }

    public Date getDateCreated() {
        return DateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        DateCreated = dateCreated;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
