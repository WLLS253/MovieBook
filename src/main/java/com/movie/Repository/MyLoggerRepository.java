package com.movie.Repository;

import com.movie.Entity.MyLogger;
import com.movie.Enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MyLoggerRepository extends JpaRepository<MyLogger,Long> {
    List<MyLogger>findMyLoggerByCreateDateAfter(Date createDate);

    List<MyLogger>findMyLoggerByUserId(String userId);

    List<MyLogger>findMyLoggerByRole(Role role);

    List<MyLogger>deleteByCreateDateAfter(Date createDate);

    List<MyLogger>findMyLoggerByCreateDateAfterAndCreateDateBefore(Date date1,Date date2);

//    Page<MyLogger>

    Page<MyLogger> findByCreateDateAfter(Date createDate, Pageable pageable);
    Page<MyLogger>findMyLoggerByUserId(String userId, Pageable pageable);
    Page<MyLogger>findMyLoggerByRole(Role role,Pageable pageable);
    Page<MyLogger>findMyLoggerByCreateDateAfterAndCreateDateBefore(Date date1,Date date2,Pageable pageable);

}
