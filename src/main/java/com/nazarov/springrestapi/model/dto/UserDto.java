package com.nazarov.springrestapi.model.dto;

import lombok.Data;

@Data
public class UserDto{
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
}