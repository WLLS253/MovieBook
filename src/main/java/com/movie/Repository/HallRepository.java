package com.movie.Repository;

import com.movie.Entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HallRepository extends JpaRepository<Hall,Long> {
}
