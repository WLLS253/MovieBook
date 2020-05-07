package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

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

    @OneToOne
    private User user;

    @OneToOne
    private Ticket ticket;


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
