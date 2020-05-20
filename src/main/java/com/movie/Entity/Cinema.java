package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Cinema extends  BaseEntity {



    @Column(unique = true)
    private String cinemaName;

    private String location;

    private String phone;

    private Integer grade;

    private String cinemaDescription;

    @Override
    public String getSearchName() {
        return this.getCinemaName();
    }

    @OneToMany(targetEntity = Figure.class)
    private List<Figure>figureList;


    public Cinema() {
        this.figureList = new ArrayList<>();
    }
}
