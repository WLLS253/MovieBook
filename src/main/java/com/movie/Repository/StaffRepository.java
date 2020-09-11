package com.movie.Repository;

import com.movie.Entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StaffRepository  extends JpaRepository<Staff,Long> {

}
