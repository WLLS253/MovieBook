package com.movie.Entity;


import com.movie.Plugins.UserPasswordEncrypt;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Assessor extends  BaseEntity {

    @Column(nullable = false)
    private  String assessorName;

    private String assessorPassword;

    private  String showImage;

    private String phone;


    public boolean checkPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return this.assessorPassword.equals(UserPasswordEncrypt.encrypt(password, "abcdef" + strTo16(this.assessorName) + "abcdef" + strTo16(this.assessorName), 233));
    }

    public String getAssessorPassword() {
        return assessorPassword;
    }

    public void setAssessorPassword(String assessorPassword) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.assessorPassword = UserPasswordEncrypt.encrypt(assessorPassword, "abcdef" + strTo16(this.assessorName) + "abcdef" + strTo16(this.assessorName), 233);;
    }

    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }





}
