package com.movie.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Ticket extends  BaseEntity {



    @Column(name = "avaliable", nullable = true)
    private Byte avaliable;


    @Column(name = "ticket_row", nullable = true)
    private Integer ticketRow;

    @Column(name = "ticket_col", nullable = true)
    private Integer ticketCol;

    private Double price;

    @OneToOne
    private Schedual schedual;


    @ManyToMany
    @JoinTable(
            name = "buy",
            joinColumns = @JoinColumn(name = "ticket_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id")
    )
    private List<User>userList;

}
