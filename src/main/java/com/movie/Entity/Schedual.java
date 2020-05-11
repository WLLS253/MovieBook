package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Schedual extends BaseEntity {

    @Column(name = "price", nullable = true, precision = 3)
    private Double price;

    @Column(name = "start_date", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;


    @Column(name = "end_date", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;


    @Column(name = "sched_description", nullable = true, length = 400)
    private String schedDescription;

    @ManyToOne(targetEntity = Cinema.class)
    private  Cinema cinema;

    @ManyToOne(targetEntity = Movie.class)
    private Movie movie;

    @ManyToOne(targetEntity = Hall.class)
    private  Hall hall;

}
