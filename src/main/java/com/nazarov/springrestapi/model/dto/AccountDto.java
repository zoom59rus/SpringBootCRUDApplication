package com.nazarov.springrestapi.model.dto;

import lombok.Data;

@Data
public class AccountDto {
    private String login;
    private String password;
}