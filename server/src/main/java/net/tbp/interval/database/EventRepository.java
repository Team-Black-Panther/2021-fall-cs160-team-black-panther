package net.tbp.interval.database;

import org.springframework.data.jpa.repository.JpaRepository;

import net.tbp.interval.backup.Event;

public interface EventRepository extends JpaRepository<Event, Integer> {

}
