package com.movie.Repository;

import com.movie.Entity.CommentEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentEsRepo extends ElasticsearchRepository<CommentEs, Long> {
    Page<CommentEs> findByMovieId(Long movieId, Pageable pageable);

    List<CommentEs> findByMovieId(Long movieId);

    List<CommentEs> findByUserId(Long userId);

    List<CommentEs> findByUserIdAndMovieId(Long userId,Long movieId);
}