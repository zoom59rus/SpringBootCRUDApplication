package com.nazarov.springrestapi.rest;

import com.nazarov.springrestapi.model.Account;
import com.nazarov.springrestapi.model.dto.AccountDto;
import com.nazarov.springrestapi.service.utils.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/")
    public ResponseEntity<?> registration(@RequestBody AccountDto accountDto){
        if(accountDto == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account account = registrationService.registrationAccount(accountDto);
        if(account != null){
            return ResponseEntity.ok(account);
        }

        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
