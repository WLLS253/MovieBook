package com.movie.Dto;


import com.movie.Entity.Figure;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class MovieDto implements Serializable {


    private Long id;

    private Double score;

    private String brief;

    private String name;

    private String releaseTime;

    private  String language;

    private  String country;

    private String duration;

    private  String showImage;

    private  String state;

    private List<Figure> figureList;

    private  String tags;

    private List<StaffDto>staffDtoList;

    @Override
    public String toString() {
        return "MovieDto{" +
                "id=" + id +
                ", score=" + score +
                ", brief='" + brief + '\'' +
                ", name='" + name + '\'' +
                ", releaseTime=" + releaseTime +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", duration='" + duration + '\'' +
                ", showImage='" + showImage + '\'' +
                ", state='" + state + '\'' +
                ", figureList=" + figureList +
                '}';
    }
}
