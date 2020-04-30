package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "takepart")
public class TakePart {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint", nullable = false)
    protected Long id;


    @Column(name = "role", nullable = false, length = 20)
    private String role;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
}
