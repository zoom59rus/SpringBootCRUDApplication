package com.nazarov.springrestapi.service;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.model.dto.AccountDto;

public interface RegistrationService {

    Account registrationAccount(AccountDto accountDto);
}
