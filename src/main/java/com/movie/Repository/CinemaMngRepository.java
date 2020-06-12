package com.movie.Repository;

import com.movie.Entity.CinemaMng;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CinemaMngRepository  extends JpaRepository<CinemaMng,Long> {
    List<CinemaMng>findByMngUsername(String name);

    @Query(nativeQuery = true,value = "SELECT distinct cm.*" +
            " FROM cinema_mng cm,cinema c" +
            " where " +
            "cm.cinema_id = c.id and "+
            "(?2 is NULL or cm.mng_sex=?2 ) and "+
            "(?1 is NULL or cm.mng_username REGEXP ?1) and " +
            "(?3 is NULL or c.cinema_name REGEXP ?3) and " +
            "(?4 is NULL or cm.prio=?4 )"
            , countQuery = "SELECT distinct count(cm.id)" +
            " FROM cinema_mng cm,cinema c" +
            " where " +
            "cm.cinema_id = c.id and "+
            "(?2 is NULL or cm.mng_sex=?2 ) and "+
            "(?1 is NULL or cm.mng_username REGEXP ?1) and " +
            "(?3 is NULL or c.cinema_name REGEXP ?3) and " +
            "(?4 is NULL or cm.prio=?4 )")
    Page<CinemaMng> filterCinemaMng(String keyString, String sex, String cinema_name, Integer prio, Pageable pageable);
}
