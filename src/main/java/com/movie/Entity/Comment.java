package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import sun.plugin2.message.PrintAppletMessage;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint", nullable = false)
    protected Long id;

    @Column(name = "title", nullable = true, length = 30)
    private String title;

    @Column(name = "content", nullable = true, length = 400)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private  Movie movie;

}
