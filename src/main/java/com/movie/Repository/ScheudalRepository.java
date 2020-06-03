package com.movie.Repository;

import com.movie.Entity.Cinema;
import com.movie.Entity.Schedual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ScheudalRepository extends JpaRepository<Schedual,Long> {
    public List<Schedual> findAllByCinema(Cinema c);
    //public List<Schedual> findALLByEndDate_YearAndEndDate_MonthAndEndDate_Day(int year,int month,int day);
    public List<Schedual> findAllByStartDate(Date date);

    @Query(nativeQuery = true,value = "select s.* from schedual s where s.cinema_id = ?1 and date(s.end_date) between ?2 and ?3 and (s.state ='paid' or s.state ='done')  in ?4 order by s.end_date")
    public List<Schedual> findAllByCinemaSchedualsByEndDate(long c_id,String start_date,String end_date);
}
