package com.movie.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@Data
@EqualsAndHashCode
public class StaffDto implements Serializable {

    private Long id;

    private String role;
    private String staffName;

    private String staffBrief;

    private String showImage;
}
