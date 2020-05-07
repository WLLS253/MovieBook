package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@EqualsAndHashCode
@Entity
public class User extends  BaseEntity {


    private String userSex;

    private String password;

    private String userEmail;

    private String username;

    private String userPhone;

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
}
