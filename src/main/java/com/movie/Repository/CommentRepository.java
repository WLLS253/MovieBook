package com.movie.Repository;

import com.movie.Entity.Comment;
import com.movie.Entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment>findByMovie(Movie movie);
}
