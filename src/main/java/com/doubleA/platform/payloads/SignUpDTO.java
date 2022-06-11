package com.doubleA.platform.payloads;

import lombok.Data;

@Data
public class SignUpDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
