package com.movie.Repository;


import com.movie.Entity.Assessor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccessorRepository extends JpaRepository<Assessor,Long> {

    public List<Assessor>findByAssessorName(String name);

}
