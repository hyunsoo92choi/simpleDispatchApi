package com.hschoi.homework.app.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hschoi.homework.app.user.entity.User;

/**
 * <pre>
 * com.hschoi.homework.app.user.repository_UserRepository.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
@Repository(value = "UserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
