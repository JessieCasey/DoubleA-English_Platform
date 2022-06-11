package com.doubleA.platform.payloads;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
}
