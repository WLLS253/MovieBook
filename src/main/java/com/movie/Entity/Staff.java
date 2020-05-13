package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Staff extends  BaseEntity {


    @Column(name = "staff_name", nullable = true, length = 20)
    private String staffName;

    @Column(name = "staff_brief", nullable = true, length = 400)
    private String staffBrief;


    private String showImage;


    @ManyToMany(mappedBy = "staffList")
    private List<Movie>movieList;

    public Staff() {
        this.movieList=new ArrayList<>();
    }
}