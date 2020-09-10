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
public class CommentDto implements Serializable {

    protected Long id;

    private String score;

    private String content;

}
