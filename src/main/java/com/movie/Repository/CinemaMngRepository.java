package com.movie.Repository;

import com.movie.Entity.CinemaMng;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaMngRepository  extends JpaRepository<CinemaMng,Long> {
    public List<CinemaMng>findByMngUsername(String name);
}
