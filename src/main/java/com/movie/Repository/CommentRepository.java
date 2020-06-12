package com.movie.Repository;

import com.movie.Entity.Comment;
import com.movie.Entity.Movie;
import com.movie.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;
import java.util.List;

import static org.hibernate.jpa.QueryHints.HINT_COMMENT;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByMovie(Movie movie);
    List<Comment> findAllByUser_IdAndMovie_Id(Long userId,Long movieId);

    @QueryHints(value = { @QueryHint(name = HINT_COMMENT, value = "a query for pageable")})
    @Query(value = "SELECT c FROM Comment c where c.movie.id=?1")
    /*@Query(nativeQuery = true,value = "SELECT c.id, FROM Comment c where c.movie.id=?1")*/
    Page<Comment> getAllByMovieOrderByCreatedTime(Long movie_id, Pageable pageable);


    List<Comment>findByUserAndMovie(User user,Movie movie);




}
