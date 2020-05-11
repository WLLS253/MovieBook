package com.movie.Entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;



@Entity
public class Figure extends  BaseEntity{

    private  String imageurl;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
