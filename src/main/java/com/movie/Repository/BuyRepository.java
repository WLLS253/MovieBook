package com.movie.Repository;

import com.movie.Entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface BuyRepository extends JpaRepository<Buy,Long> {

    int countAllByStateAndPurchaseDateBeforeAndPurchaseDateAfter(String state, Date before,Date after);

}
