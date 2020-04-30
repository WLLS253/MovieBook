package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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


    @ManyToMany(mappedBy = "userList")
    private  List<Ticket>ticketList;


}
