package com.lyacoin.api.auth.filters;


import com.lyacoin.api.auth.token.JwtTokenUtil;
import com.lyacoin.api.config.GlobalCustomContext;
import com.lyacoin.api.core.model.User;
import com.lyacoin.api.exception.AppException;
import com.lyacoin.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@Slf4j
public class ParseTokenFilter extends GenericFilterBean {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private GlobalCustomContext customContext;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            log.info("Begin process parse token");
            final HttpServletRequest httpServletRequest = (HttpServletRequest) request;


            final String authorization = httpServletRequest.getHeader("Authorization");
            final HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            log.info("Auth token: " + authorization);

            if (authorization == null) {
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }

            final String[] tokenPart = authorization.split("Bearer ");
            if (tokenPart.length < 2) {
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }

            final String token = tokenPart[1];
            final String id = tokenUtil.getIdFromToken(token);
            log.info("Request from : " + id);

            final User user = userService.findById(id)
                    .orElseThrow(RuntimeException::new);

            customContext.user = user;

            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("Error after parse token", e);
            ((HttpServletResponse)response).setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }
    }
}
