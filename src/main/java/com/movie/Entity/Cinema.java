package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

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


}
