package com.techy.user.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techy.user.domain.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, String> {
	Optional<User> findByUserName(String userName);
}
