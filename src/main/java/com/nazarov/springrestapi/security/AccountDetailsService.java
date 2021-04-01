package com.nazarov.springrestapi.security;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.repository.AccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("accountDetailsService")
public class AccountDetailsService implements UserDetailsService{

    private final AccountRepository accountRepository;

    public AccountDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
            Account account = accountRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException(
                    "User by login " + login + " not found."
            ));

            return AccountDetails.fromAccount(account);
    }
}