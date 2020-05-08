package com.movie.Repository;

import com.movie.Entity.Tokeners;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TokenersRepository extends JpaRepository<Tokeners,Long> {

    List<Tokeners>findByUuid(String uuid);

     List<Tokeners>findAllByToken(String token);
}
