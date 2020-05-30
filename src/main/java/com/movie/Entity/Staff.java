package com.movie.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Entity
public class Staff extends  BaseEntity {


    @Column(name = "staff_name", nullable = true, length = 20)
    private String staffName;

    @Column(name = "staff_brief", nullable = true, length = 400)
    private String staffBrief;


    private String showImage;


    @JsonIgnore
    @ManyToMany(mappedBy = "staffList")
    private List<Movie>movieList;

    public Staff() {
        this.movieList=new ArrayList<>();
    }

    public void  updateObject(Object o) {
        Staff staff = (Staff) o;
        if(staff.staffName!=null){
            staffName=staff.staffName;
        }
        if( staff.staffBrief!=null){
            staffBrief=staff.staffBrief;
        }
        if(staff.showImage!=null){
            showImage=staff.showImage;
        }

    }
}
