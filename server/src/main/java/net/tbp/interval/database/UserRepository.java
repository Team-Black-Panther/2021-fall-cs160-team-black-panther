package net.tbp.interval.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.tbp.interval.backup.UserProfile;

public interface UserRepository extends JpaRepository<UserProfile, Integer> {
	
	@Query("SELECT e FROM UserProfile e WHERE username = :target")
	UserProfile findProfileByName(@Param("target") String username);
}
