package com.movie.Entity;


import com.movie.Enums.Role;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.util.Date;


@Setter
@Getter
@EqualsAndHashCode
@Entity
public class MyLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigint", nullable = false)
    private Long id;

    private String ip;

    private String userId;

    private String operation;

    private Role role;

    private String method;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    private  String params;

    @Override
    public String toString() {
        return "MyLogger{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", userId='" + userId + '\'' +
                ", operation='" + operation + '\'' +
                ", role=" + role +
                ", method='" + method + '\'' +
                ", createDate=" + createDate +
                ", params='" + params + '\'' +
                '}';
    }
}
