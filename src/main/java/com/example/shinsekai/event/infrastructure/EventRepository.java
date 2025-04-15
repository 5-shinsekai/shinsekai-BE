package com.example.shinsekai.event.infrastructure;

import com.example.shinsekai.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
}
