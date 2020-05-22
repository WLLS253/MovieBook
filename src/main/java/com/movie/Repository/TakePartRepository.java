package com.movie.Repository;

import com.movie.Entity.Movie;
import com.movie.Entity.TakePart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TakePartRepository extends JpaRepository<TakePart,Long> {
    public List<TakePart> findAllByMovie_Id(Long movie_id);
}
