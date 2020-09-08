package com.movie.Repository;

import com.movie.Entity.Cinema;
import com.movie.Entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface CinemaRepository extends JpaRepository<Cinema,Long> {

    public List<Cinema>findByCinemaName(String name);

    @Query(nativeQuery = true,value="select c.id as cinema_id ,c.cinema_name,s.id as sched_id,s.start_date,c.location as location from cinema c join schedual s on c.id = s.cinema_id  " +
            "where s.movie_id = :movie_id " +
            "and s.state = 'normal'  " +
            "and (:date_info is NULL or (year(:date_info) = year(s.start_date) and month(:date_info) = month(s.start_date)  and DAY(:date_info) = DAY(s.start_date)))" +
            "order by s.start_date asc")
    public List<Object[]> getOnShowCinemas(@Param("movie_id") Long movie_id, @Param("date_info") Date date_info);



    @Query(nativeQuery = true,value = "SELECT distinct c.*" +
            " FROM cinema c " +
            " where " +
            "(?1 is NULL or c.grade=?1 )"
            , countQuery = "SELECT distinct c.*" +
            " FROM cinema c " +
            " where " +
            "(?1 is NULL or c.grade=?1 )")
    List<Cinema> filterCinema(Integer grade);

    @Query(nativeQuery = true,value = "SELECT distinct c.*" +
            " FROM cinema c " +
            " where " +
            "(?1 is NULL or c.grade=?1 ) and "+
            "(?2 is NULL or c.cinema_name REGEXP ?2) and " +
            "(?3 is NULL or c.location REGEXP ?3)"
            , countQuery = "SELECT distinct c.*" +
            " FROM cinema c " +
            " where " +
            "(?1 is NULL or c.grade=?1 ) and " +
            "(?2 is NULL or c.cinema_name REGEXP ?2 ) and " +
            "(?3 is NULL or c.location REGEXP ?3 )")
    Page<Cinema> filterCinema(Integer grade, String key_strings , String loc_strings, Pageable pageable);
}
