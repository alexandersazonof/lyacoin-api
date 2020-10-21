package com.lyacoin.api.rest;

import com.lyacoin.api.auth.token.JwtTokenUtil;
import com.lyacoin.api.core.dto.TokenResponse;
import com.lyacoin.api.core.dto.UserRegistrationDto;
import com.lyacoin.api.core.model.User;
import com.lyacoin.api.exception.AppException;
import com.lyacoin.api.exception.ExceptionMessageClient;
import com.lyacoin.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody UserRegistrationDto userDto) {
        final User user = userService.findBySeed(userDto.getSeed()).orElseThrow(() ->
                new AppException(ExceptionMessageClient.INCORRECT_SEED, "Seed doesn't exist"));

        return TokenResponse.builder()
                .token(jwtTokenUtil.generateToken(user))
                .build();
    }

    @PostMapping("/registration")
    public TokenResponse registration(@RequestBody UserRegistrationDto userDto) {
        return TokenResponse.builder()
                .token(jwtTokenUtil.generateToken(userService.save(userDto)))
                .build();
    }
}
