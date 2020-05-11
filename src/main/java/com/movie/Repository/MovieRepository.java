package com.movie.Repository;

import com.movie.Entity.Movie;
//import com.movie.Entity.;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    //@Query(value = "SELECT distinct m FROM Tag t,Mark ma,Movie m  Where ma.movie_id=m.id and t.id = ma.tag_id and t.tag_name in :tags")
    @Query(nativeQuery = true,value = "SELECT distinct m.* FROM tag t,mark ma,movie m  Where ma.movie_id=m.id and t.id = ma.tag_id and t.tag_name in :tags and YEAR(m.release_time) between :start_year and :end_year")
    List<Movie> filterMovies(@Param("tags") List<String> tags,@Param("start_year") int start_year,@Param("end_year")int end_year);
}
