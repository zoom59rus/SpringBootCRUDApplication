package com.nazarov.springrestapi.service.impl;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.model.Role;
import com.nazarov.springrestapi.model.dto.AccountDto;
import com.nazarov.springrestapi.model.enums.AccountStatus;
import com.nazarov.springrestapi.model.enums.RoleNames;
import com.nazarov.springrestapi.repository.AccountRepository;
import com.nazarov.springrestapi.repository.RoleRepository;
import com.nazarov.springrestapi.service.utils.RegistrationService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public RegistrationServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Account registrationAccount(AccountDto accountDto) {
        Account account = new Account();
        account.setLogin(accountDto.getLogin());
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        account.setStatus(AccountStatus.ACTIVE);

        Role role = roleRepository.findByName(RoleNames.USER);

        account.getRoles().add(role);
        role.getAccounts().add(account);

        return accountRepository.save(account);
    }
}