package com.movie.Entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.Objects;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Assessor extends  BaseEntity {



    @Column(nullable = false)
    private  String assessorName;

    private String assessorPassword;


}
