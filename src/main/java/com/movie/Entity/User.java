package com.movie.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movie.Plugins.UserPasswordEncrypt;
import com.sun.javafx.beans.IDProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode
@Entity
public class User extends  BaseEntity {


    private String userSex;


    @JsonIgnore
    private String password;

    private String userEmail;

    @Column(unique=true)
    private String username;

    private String userPhone;

    private String showimage;

    @ManyToMany
    @JoinTable(
            name = "comment",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "movie_id",referencedColumnName = "id")
    )
    @JsonIgnore
    private List<Movie> commentedMovies;


    @ManyToMany
    @JoinTable(
            name = "collect",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "movie_id",referencedColumnName = "id")
    )
    @JsonIgnore
    private List<Movie> collectedMovies;


//    @OneToMany(mappedBy = "user")
//    private  List<Ticket>ticketList;

    public User() {
        this.commentedMovies = new ArrayList<>();
        this.collectedMovies = new ArrayList<>();
    }


    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        this.password = UserPasswordEncrypt.encrypt(password, "abcdef" + strTo16("78") + "abcdef" + strTo16("78"), 233);;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }


    public List<Movie> getCommentedMovies() {
        return commentedMovies;
    }

    public void setCommentedMovies(List<Movie> commentedMovies) {
        this.commentedMovies = commentedMovies;
    }

    public List<Movie> getCollectedMovies() {
        return collectedMovies;
    }

    public void setCollectedMovies(List<Movie> commentedMovies) {
        this.collectedMovies= commentedMovies;
    }




    public boolean checkPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        return this.password.equals(UserPasswordEncrypt.encrypt(password, "abcdef" + strTo16("78") + "abcdef" + strTo16("78"), 233));
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

    public String getShowimage() {
        return showimage;
    }

    public void setShowimage(String showimage) {
        this.showimage = showimage;
    }

//    @Override
//    public String toString() {
//        return "User{" +
//                "userSex='" + userSex + '\'' +
//                ", password='" + password + '\'' +
//                ", userEmail='" + userEmail + '\'' +
//                ", username='" + username + '\'' +
//                ", userPhone='" + userPhone + '\'' +
//                ", commentedMovies=" + commentedMovies +
//                ", id=" + id +
//                ", createdTime=" + createdTime +
//                ", updatedTime=" + updatedTime +
//                '}';
//    }


    @Override
    public String toString() {
        return "User{" +
                "userSex='" + userSex + '\'' +
                ", password='" + password + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", showimage='" + showimage + '\'' +
                ", commentedMovies=" + commentedMovies +
                ", collectedMovies=" + collectedMovies +
                ", id=" + id +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
