package com.main.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AuthorizeHandler {
    private final ArrayList<String> authorizedCookies;
    public AuthorizeHandler() {
        authorizedCookies = new ArrayList<>();
    }

    private String extractSessionIdFromHttpRequest(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        return httpSession.getId();
    }

    public boolean isAuthorized(HttpServletRequest httpServletRequest) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        return authorizedCookies.contains(sessionId);
    }

    public void addCookieFromHttpSession(HttpServletRequest httpServletRequest) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        authorizedCookies.add(sessionId);
    }

    public void removeCookieFromHttpSession(HttpServletRequest httpServletRequest) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        authorizedCookies.remove(sessionId);
    }
}
