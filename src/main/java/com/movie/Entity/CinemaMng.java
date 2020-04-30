package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.List;


@Setter
@Getter
@EqualsAndHashCode
@Entity
public class CinemaMng extends BaseEntity{



    @Column(name = "mng_phone", nullable = true, length = 11)
    private String mngPhone;

    @Column(name = "prio", nullable = true)
    private Integer prio;

    @Column(name = "mng_username", nullable = true, length = 30)
    private String mngUsername;

    @Column(name = "mng_email", nullable = true, length = 30)
    private String mngEmail;

    @Column(name = "mng_password", nullable = true, length = 30)
    private String mngPassword;

    @Column(name = "mng_sex", nullable = true, length = 1)
    private String mngSex;

    @ManyToOne(targetEntity = Cinema.class)
    private  Cinema cinema;

}
