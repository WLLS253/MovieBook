package com.movie.Repository;

import com.movie.Entity.MyLogger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyLoggerRepository extends JpaRepository<MyLogger,Long> {
}
