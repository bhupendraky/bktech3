package com.bktech.user.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bktech.user.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	boolean existsByName(String name);

	Optional<Role> findByName(String name);

}
