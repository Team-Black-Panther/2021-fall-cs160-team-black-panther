package net.tbp.interval.database;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.tbp.interval.backup.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	@Query("SELECT e FROM Task e WHERE owner = :uid")
	List<Task> findAllActiveTasks(@Param("uid") Integer uid);

}
