package com.movie.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Tag extends  BaseEntity {



    @Column(name = "tag_name", nullable = true, length = 20)
    private String tagName;

    @JsonIgnore
    @ManyToMany(mappedBy = "tagList")
    private List<Movie>movieList;

    public Tag() {
        this.movieList = new ArrayList<>();
    }
}
