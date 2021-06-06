package com.lyacoin.api.service;

import com.lyacoin.api.config.GlobalCustomContext;
import com.lyacoin.api.core.dto.SessionRequest;
import com.lyacoin.api.core.dto.SessionResponse;
import com.lyacoin.api.core.model.session.Session;
import com.lyacoin.api.core.model.session.SessionAction;
import com.lyacoin.api.exception.IncorrectSessionException;
import com.lyacoin.api.repository.SessionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private GlobalCustomContext context;

    public void save(Session session) {
        sessionRepository.save(session);
    }

    public SessionResponse verify(SessionRequest sessionRequest) {
        Session session =  Session.builder().build();
        try {
            final String token = context.token;
            final String userId = context.getUserId();
            int count = 0;
            SessionAction action = SessionAction.CORRECT;

            session = sessionRepository.findByTokenAndUserId(token, userId)
                    .orElseThrow(IncorrectSessionException::new);

            int sessionCountAttempts = session.getCountAttempts();

            if (sessionCountAttempts > 3) {
                throw new IncorrectSessionException();
            }

            boolean result = session.getPassword().equals(sessionRequest.getPassword());

            if (!result) {
                count = sessionCountAttempts + 1;
                action = SessionAction.INCORRECT;
            }

            session.setCountAttempts(count);
            sessionRepository.save(session);

            return SessionResponse.builder()
                    .result(action)
                    .build();

        } catch (IncorrectSessionException e) {
            log.error("Incorrect session from: " + context.user);
            return removeAndLogout(session);
        }
    }

    private SessionResponse removeAndLogout(Session session) {
        if (session != null && session.getId() != null) {
            sessionRepository.deleteById(session.getId());
        }
        return SessionResponse.builder()
                .result(SessionAction.LOGOUT)
                .build();
    }
}
