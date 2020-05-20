package com.movie.Repository;

import com.movie.Entity.Figure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FigureRepository extends JpaRepository<Figure,Long> {
}
