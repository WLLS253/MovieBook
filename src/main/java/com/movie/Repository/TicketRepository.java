package com.movie.Repository;

import com.movie.Entity.Schedual;
import com.movie.Entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Long> {

    public List<Ticket>findBySchedual(Schedual schedual);
}
