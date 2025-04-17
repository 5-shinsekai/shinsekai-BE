package com.example.shinsekai.event.infrastructure;

import com.example.shinsekai.event.entity.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.id = :id AND CURRENT_DATE BETWEEN e.startDate AND e.endDate")
    Optional<Event> findOngoingEventById(@Param("id") Integer id);

    @Query("SELECT e FROM Event e WHERE CURRENT_DATE BETWEEN e.startDate AND e.endDate ORDER BY e.startDate DESC")
    List<Event> findAllOngoingEvents(Pageable pageable);
}
