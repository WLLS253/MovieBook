package com.movie.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Buy extends BaseEntity {



    @Column(name = "purchase_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @Column(name = "state", nullable = false, length = 20)
    private String state;

    private Double price;

    @JsonIgnore
    @ManyToOne
    private Schedual schedual;

    @ManyToOne
    private User user;

    //一次购买可以买多张票
    @ManyToMany
    @JoinTable(name = "buy_tickets", joinColumns = @JoinColumn(name = "buy_id"), inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    private List<Ticket> tickets;


    @Override
    public String toString() {
        return "Buy{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", state='" + state + '\'' +
//                ", user=" + user +
//                ", ticket=" + ticket +
                '}';
    }
}
