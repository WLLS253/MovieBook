package com.movie.Dto;


import com.movie.Entity.Figure;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CinemaDto  implements Serializable {

    protected Long id;

    private String cinemaName;

    private String location;

    private String phone;

    private Integer grade;

    private String cinemaDescription;
    private String cover_img_url;


    private List<Figure> figureList;
}
