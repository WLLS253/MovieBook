package com.movie.Repository;

import com.movie.Entity.Cinema;
import com.movie.Entity.Schedual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheudalRepository extends JpaRepository<Schedual,Long> {
    public List<Schedual> findAllByCinema(Cinema c);
}
