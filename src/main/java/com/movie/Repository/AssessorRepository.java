package com.movie.Repository;


import com.movie.Entity.Assessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssessorRepository extends JpaRepository<Assessor,Long> {
    List<Assessor>findByAssessorName(String name);

}
