package net.tbp.interval.database;

import org.springframework.data.jpa.repository.JpaRepository;

import net.tbp.interval.backup.UserProfile;

public interface UserRepository extends JpaRepository<UserProfile, Integer> {

}
