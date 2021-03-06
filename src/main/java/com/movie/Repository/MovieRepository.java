package com.movie.Repository;

import com.movie.Entity.Movie;
//import com.movie.Entity.;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

public interface    MovieRepository extends JpaRepository<Movie,Long> {
    //@Query(value = "SELECT distinct m FROM Tag t,Mark ma,Movie m  Where ma.movie_id=m.id and t.id = ma.tag_id and t.tag_name in :tags")
    @Query(nativeQuery = true,value = "SELECT distinct m.* FROM tag t,mark ma,movie m  Where ma.movie_id=m.id and t.id = ma.tag_id and t.tag_name in :tags and YEAR(m.release_time) between :start_year and :end_year")
    List<Movie> filterMovies(@Param("tags") List<String> tags,@Param("start_year") int start_year,@Param("end_year")int end_year);

    @Query(nativeQuery = true,value = "SELECT distinct m.* FROM movie m  Where YEAR(m.release_time) between :start_year and :end_year")
    List<Movie> filterMovies(@Param("start_year") int start_year,@Param("end_year")int end_year);

    //m.id,m.created_time,m.updated_time,m.brief,m.release_time,m.score,m.show_image,m.name,m.country,m.duration,m.language,m.state
    @Query(nativeQuery = true,value = "SELECT distinct m.*" +
            " FROM movie m left join mark ma on ma.movie_id = m.id left join tag t on t.id = ma.tag_id left join schedual s on s.movie_id=m.id left join cinema ci on s.cinema_id = ci.id" +
            " where " +
            "(?6 is NUll or ci.cinema_name REGEXP ?6 ) and " +
            //"(?3 = ('null') or  t.tag_name in ?3) and " +
            "(?3 is NULL or m.tags REGEXP ?3) and " +
            "(?1 is null or ?2 is null or year(m.release_time) between ?1 and ?2) and " +
            "(?5 is null or m.state = ?5) and "+
            "(?4 is null or (year(?4)=year(s.start_date) and month(?4)= month(s.start_date) and day(?4)= day(s.start_date))) and "+
            "(?7 is NULL or m.name REGEXP ?7 ) and "+
            "(?8 is null or ?8= m.country) "
            , countQuery = "SELECT count(distinct m.id)" +
            " FROM movie m left join mark ma on ma.movie_id = m.id left join tag t on t.id = ma.tag_id left join schedual s on s.movie_id=m.id left join cinema ci on s.cinema_id = ci.id" +
            " where " +
            "(?6 is NUll or ci.cinema_name REGEXP ?6 ) and " +
            //"(?3 = ('null') or  t.tag_name in ?3) and " +
            "(?3 is NULL or m.tags REGEXP ?3) and " +
            "(?1 is null or ?2 is null or year(m.release_time) between ?1 and ?2) and " +
            "(?5 is null or m.state = ?5) and "+
            "(?4 is null or ((year(?4)=year(s.start_date) and month(?4)= month(s.start_date) and day(?4)= day(s.start_date)))) and "+
            "(?7 is NULL or m.name REGEXP ?7 ) and "+
            "(?8 is null or ?8= m.country) ")
    Page<Movie> filterMovies(Integer start_year,
                             Integer end_year,
                             String tags,
                             Date date,
                             String state,
                             String cinema_name,
                             String movie_name,
                             String country,Pageable pageable);

    @Query(nativeQuery = true,value = "SELECT m.* FROM movie m JOIN takepart t ON m.id=t.movie_id Join staff s ON s.id = t.staff_id " +
            "where " +
            "(?1 is NUll or t.role=?1) and "+
            "s.staff_name = ?2")
    List<Movie> filterMovies(String role ,String staff_name);

    @Query(nativeQuery = true,value = "SELECT m.* from movie m where m.state = :state order by release_time asc limit :start,:num")
    List<Movie> getLimitMoviesByState(@Param("state")String state,@Param("start")int start,@Param("num")int num);

    List<Movie> getMoviesByState(String state);



    // for recommender
    @Query(nativeQuery = true, value = "select * from movie where 1=1 limit ?1")
    List<Movie> findAllByCountLimit(@Param("num") int num);

    @Query(nativeQuery = true, value = "SELECT * FROM movie WHERE 1=1 ORDER BY score DESC limit 12")
    List<Movie> findAllByHighScore();

}
