package com.lyacoin.api.core.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SessionRequest {

    @NotEmpty
    private String password;
}
