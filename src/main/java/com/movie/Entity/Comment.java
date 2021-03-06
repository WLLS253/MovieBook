package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "comment")
public class Comment extends BaseEntity{




    @Column(name = "score", nullable = true)
    private Float score;

    // 赞同数
    @Column(name = "votes", nullable = true)
    private Long votes;






    @Column(name = "content", nullable = true, length = 400)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "movie_id")
    private  Movie movie;

}
