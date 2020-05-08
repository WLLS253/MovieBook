package com.movie.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Ticket extends  BaseEntity {



    @Column(name = "avaliable", nullable = true)
    private boolean avaliable;


    @Column(name = "ticket_row", nullable = true)
    private Integer ticketRow;

    @Column(name = "ticket_col", nullable = true)
    private Integer ticketCol;

    private Double price;

    @OneToOne
    private Schedual schedual;


    @ManyToOne(targetEntity = User.class)
//    @JoinTable(
//            name = "buy",
//            joinColumns = @JoinColumn(name = "ticket_id",referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
//    )
    private User user;


    @Override
    public String toString() {
        return "Ticket{" +
                "avaliable=" + avaliable +
                ", ticketRow=" + ticketRow +
                ", ticketCol=" + ticketCol +
                ", price=" + price +
                ", schedual=" + schedual +
                ", user=" + user +
                ", id=" + id +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
