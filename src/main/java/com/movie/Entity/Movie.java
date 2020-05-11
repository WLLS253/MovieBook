package com.movie.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Movie extends BaseEntity {



    @Column(name = "score", nullable = true, precision = 1)
    private Double score;

    @Column(name = "brief", nullable = true, length = 400)
    private String brief;

    @Column(name = "release_time", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseTime;


    @ManyToMany
    @JoinTable(
            name = "takepart",
            joinColumns = @JoinColumn(name = "movie_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "staff_id",referencedColumnName = "id")
    )
    private List<Staff>staffList;


    @JsonIgnore
    @ManyToMany(mappedBy = "movieList")
    private List<User>userList;

    @ManyToMany
    @JoinTable(
            name ="mark",
            joinColumns = @JoinColumn(name = "movie_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id",referencedColumnName = "id")
    )
    private  List<Tag> tagList;

    public Movie() {
        this.staffList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.tagList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Movie{" +
                "score=" + score +
                ", brief='" + brief + '\'' +
                ", releaseTime=" + releaseTime +
                ", staffList=" + staffList +
                ", userList=" + userList +
                ", tagList=" + tagList +
                ", id=" + id +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
