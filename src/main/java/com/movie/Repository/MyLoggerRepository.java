package com.movie.Repository;

import com.movie.Entity.MyLogger;
import com.movie.Enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MyLoggerRepository extends JpaRepository<MyLogger,Long> {
    List<MyLogger>findMyLoggerByCreateDateAfter(Date createDate);

    List<MyLogger>findMyLoggerByUserId(String userId);

    List<MyLogger>findMyLoggerByRole(Role role);

    List<MyLogger>deleteByCreateDateAfter(Date createDate);
}
