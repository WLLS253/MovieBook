package com.movie.Dto;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class UserInfoDto implements Serializable {

    private Long id;

    private String userSex;

    private String userEmail;

    @Column(unique=true)
    private String username;

    private String userPhone;

    private String showimage;
}