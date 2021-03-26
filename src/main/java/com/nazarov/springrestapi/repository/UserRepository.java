package com.nazarov.springrestapi.repository;

import com.nazarov.springrestapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
