package com.example.mobilebankingapi.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.stereotype.Component;

@Component
public class JwtFailureEvents {
    private HttpServletResponse response;

    @Autowired
    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;


    }

    @EventListener
    public void OnFailure(AuthenticationFailureBadCredentialsEvent badCredentialsEvent) {
        if (badCredentialsEvent.getAuthentication() instanceof BearerTokenAuthentication) {
            System.out.println(badCredentialsEvent.getException().getMessage());
        }
    }
}


