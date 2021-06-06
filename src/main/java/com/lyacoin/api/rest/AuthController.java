package com.lyacoin.api.rest;

import com.lyacoin.api.auth.token.JwtTokenUtil;
import com.lyacoin.api.core.dto.TokenResponse;
import com.lyacoin.api.core.dto.UserDataDto;
import com.lyacoin.api.core.model.session.Session;
import com.lyacoin.api.core.model.User;
import com.lyacoin.api.exception.AppException;
import com.lyacoin.api.exception.ExceptionMessageClient;
import com.lyacoin.api.service.SessionService;
import com.lyacoin.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private SessionService sessionService;


    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody UserDataDto userDto) {
        final User user = userService.findBySeed(userDto.getSeed()).orElseThrow(() ->
                new AppException(ExceptionMessageClient.INCORRECT_SEED, "Seed doesn't exist"));
        final String token = jwtTokenUtil.generateToken(user);

        sessionService.save(Session.builder()
                .countAttempts(0)
                .password(userDto.getPassword())
                .token(token)
                .user(user)
                .build());

        return TokenResponse.builder()
                .token(token)
                .build();
    }

    @PostMapping("/registration")
    public TokenResponse registration(@Valid @RequestBody UserDataDto userDto) {
        final User user = userService.save(userDto);
        final String token = jwtTokenUtil.generateToken(user);

        sessionService.save(Session.builder()
                .countAttempts(0)
                .password(userDto.getPassword())
                .token(token)
                .user(user)
                .build());

        return TokenResponse.builder()
                .token(token)
                .build();
    }
}
