package com.nazarov.springrestapi.service.impl;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.model.Role;
import com.nazarov.springrestapi.model.dto.AccountDto;
import com.nazarov.springrestapi.model.enums.AccountStatus;
import com.nazarov.springrestapi.model.enums.RoleNames;
import com.nazarov.springrestapi.repository.AccountRepository;
import com.nazarov.springrestapi.repository.RoleRepository;
import com.nazarov.springrestapi.service.utils.RegistrationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;

    public RegistrationServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Account registrationAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setLogin(accountDto.getLogin());
        account.setPassword(accountDto.getPassword());
        account.setStatus(AccountStatus.ACTIVE);

        Role role = roleRepository.findByName(RoleNames.ROLE_USER);

        account.getRoles().add(role);
        role.getAccounts().add(account);

        return accountRepository.save(account);
    }
}