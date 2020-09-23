package com.lyacoin.api.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRegistrationDto {
    private List<String> seed;
}
