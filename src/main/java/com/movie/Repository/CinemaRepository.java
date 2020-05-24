package com.movie.Repository;

import com.movie.Entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface CinemaRepository extends JpaRepository<Cinema,Long> {

    public List<Cinema>findByCinemaName(String name);

    @Query(nativeQuery = true,value="select c.id as cinema_id ,c.cinema_name,s.id as sched_id,s.start_date from cinema c join schedual s on c.id = s.cinema_id  " +
            "where s.movie_id = :movie_id " +
            "and s.state = 'normal'  " +
            "and (:date_info is NULL or (:date_info<s.start_date and DAY(:date_info) = DAY(s.start_date)))" +
            "order by s.start_date asc")
    public List<Object[]> getOnShowCinemas(@Param("movie_id") Long movie_id, @Param("date_info") Date date_info);
}
