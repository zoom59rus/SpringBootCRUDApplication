package com.nazarov.springrestapi.repository;

import com.nazarov.springrestapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> getEventsByFileId(Long id);
    List<Event> getEventsByFileName(String name);
}
