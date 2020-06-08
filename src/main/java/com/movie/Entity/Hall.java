package com.movie.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Hall extends BaseEntity {




    @Column(name = "col", nullable = true)
    private Integer col;

    @Column(name = "row", nullable = true)
    private Integer row;

    @Column(name = "hall_type", nullable = true, length = 20)
    private String hallType;

    @Column(name = "layout", nullable = true, length = 1024)
    private String layout;

    @Column(name = "hall_name", nullable = true, length = 20)
    private String hallName;


    @ManyToOne(targetEntity = Cinema.class)
    private  Cinema cinema;


    @OneToMany(targetEntity = Figure.class)
    private List<Figure>figureList;


    @Override
    public String toString() {
        return "Hall{" +
                "col=" + col +
                ", row=" + row +
                ", hallType='" + hallType + '\'' +
                ", layout='" + layout + '\'' +
                ", hallName='" + hallName + '\'' +
                ", cinema=" + cinema +
                ", id=" + id +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                '}';
    }


    public void  updateObject(Object o) {
        Hall hall = (Hall) o;
        if(hall.hallName!=null){
            hallName=hall.hallName;
        }
        if( hall.hallType!=null){
            hallType=hall.hallType;
        }
        if(hall.row!=null){
            row=hall.row;
        }
        if(hall.col!=null){
            col=hall.col;
        }
        if(hall.layout!=null){
            layout=hall.layout;
        }

    }
}
