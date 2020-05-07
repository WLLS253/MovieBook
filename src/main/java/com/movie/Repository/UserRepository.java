package com.movie.Repository;

import com.movie.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository< User,Long> {

    public List<User>findByUsername(String name);


}
