package com.movie.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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
}
