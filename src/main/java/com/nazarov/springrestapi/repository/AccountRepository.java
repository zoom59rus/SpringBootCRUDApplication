package com.nazarov.springrestapi.repository;

import com.nazarov.springrestapi.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
