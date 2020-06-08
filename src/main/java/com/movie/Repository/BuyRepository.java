package com.movie.Repository;

import com.movie.Entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import java.util.Date;

public interface BuyRepository extends JpaRepository<Buy,Long> {
    @Query(nativeQuery = true,value = "select b.* from buy b join schedual s on b.schedual_id = s.id where date(s.end_date) = ?2 and b.state != 'refund' and s.cinema_id = ?1")
    public List<Buy> getIncomeStatistic(long c_id,String end_date);

    @Query(nativeQuery = true,value = "select b.* from buy b join schedual s on b.schedual_id = s.id where year(s.end_date) = ?2 and b.state != 'refund' and s.cinema_id = ?1")
    public List<Buy> getIncomeStatistic(long c_id,int year);
    int countAllByStateAndPurchaseDateBeforeAndPurchaseDateAfter(String state, Date before,Date after);


}
