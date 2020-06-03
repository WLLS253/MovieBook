package com.movie.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    //该 日程安排的 状态
        // normal 正常， dated 过期， deprecated 弃用
    @Column(name="state",nullable = true,length = 20)
    private String state;


    @ManyToOne(targetEntity = Cinema.class)
    private  Cinema cinema;

    @ManyToOne(targetEntity = Movie.class)
    private Movie movie;

    @ManyToOne(targetEntity = Hall.class)
    private  Hall hall;


    @JsonIgnore
    @OneToMany(targetEntity = Ticket.class)
    @JoinColumn(name = "schedual_id")
    public List<Ticket> ticketList;

    @JsonIgnore
    @OneToMany(targetEntity = Buy.class)
    @JoinColumn(name = "schedual_id")
    public List<Buy> buyList;

}
