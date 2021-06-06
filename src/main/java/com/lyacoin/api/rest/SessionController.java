package com.lyacoin.api.rest;

import com.lyacoin.api.core.dto.SessionRequest;
import com.lyacoin.api.core.dto.SessionResponse;
import com.lyacoin.api.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("sessions")
@CrossOrigin("*")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/check")
    public SessionResponse verify(@Valid @RequestBody SessionRequest sessionRequest) {
        return sessionService.verify(sessionRequest);
    }
}
