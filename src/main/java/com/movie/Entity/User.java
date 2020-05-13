package com.movie.Entity;


import com.movie.Plugins.UserPasswordEncrypt;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;


@EqualsAndHashCode
@Entity
public class User extends  BaseEntity {


    private String userSex;

    private String password;

    private String userEmail;

    private String username;

    private String userPhone;

    private String showimage;


    @ManyToMany
    @JoinTable(
            name = "comment",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns =@JoinColumn(name = "movie_id",referencedColumnName = "id")
    )
    private List<Movie>movieList;



//    @OneToMany(mappedBy = "user")
//    private  List<Ticket>ticketList;

    public User() {
        this.movieList = new ArrayList<>();
//        this.ticketList = new ArrayList<>();
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
        this.password = UserPasswordEncrypt.encrypt(password, "abcdef" + strTo16(this.username) + "abcdef" + strTo16(this.username), 233);;
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

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }


    public boolean checkPassword(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        return this.password.equals(UserPasswordEncrypt.encrypt(password, "abcdef" + strTo16(this.username) + "abcdef" + strTo16(this.username), 233));
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

    @Override
    public String toString() {
        return "User{" +
                "userSex='" + userSex + '\'' +
                ", password='" + password + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", username='" + username + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", movieList=" + movieList +
                ", id=" + id +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }


}