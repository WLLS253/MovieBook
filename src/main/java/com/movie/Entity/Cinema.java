package com.movie.Entity;


import com.movie.Serivce.UploadSerivce;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Cinema extends  BaseEntity {



    @Column(unique = true)
    private String cinemaName;

    private String location;

    private String phone;

    private Integer grade;

    private String cinemaDescription;
    private String cover_img_url;

    @Override
    public String getSearchName() {
        return this.getCinemaName();
    }

    @OneToMany(targetEntity = Figure.class)
    private List<Figure>figureList;

/*    public String getCover_img_url(){
        String[] strs= this.cover_img_url.split("/");
        return UploadSerivce.uploadServer+ strs[strs.length-1];
    }*/



    public Cinema() {
        this.figureList = new ArrayList<>();
    }
}
