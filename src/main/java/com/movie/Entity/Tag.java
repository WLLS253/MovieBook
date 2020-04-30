package com.movie.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Setter
@Getter
@Entity
public class Tag extends  BaseEntity {



    @Column(name = "tag_name", nullable = true, length = 20)
    private String tagName;

    @ManyToMany(mappedBy = "tagList")
    private List<Movie>movieList;

}
