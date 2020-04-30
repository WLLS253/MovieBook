package com.movie.Repository;

import com.movie.Entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CinemaRepository extends JpaRepository<Cinema,Long> {

    public List<Cinema>findByCinemaName(String name);
}
