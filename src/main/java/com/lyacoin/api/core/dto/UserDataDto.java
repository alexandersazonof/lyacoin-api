package com.lyacoin.api.core.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserDataDto {

    @NotNull
    private List<String> seed;

    @NotEmpty
    private String password;
}
