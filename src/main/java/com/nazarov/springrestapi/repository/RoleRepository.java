package com.nazarov.springrestapi.repository;

import com.nazarov.springrestapi.model.Role;
import com.nazarov.springrestapi.model.enums.RoleNames;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleNames name);
}
