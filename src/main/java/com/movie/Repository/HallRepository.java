package com.movie.Repository;

import com.movie.Entity.Cinema;
import com.movie.Entity.Hall;
import org.apache.catalina.LifecycleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall,Long> {

    public List<Hall>findByCinema(Cinema cinema);
    Page<Hall> findByCinema(Cinema cinema, Pageable pageable);
}
