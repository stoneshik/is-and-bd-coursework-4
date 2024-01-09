package com.main.security;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class AuthorizeHandler {
    private final ArrayList<String> sessionIds;
    private final HashMap<String, String> loginsBySessionIds;

    public AuthorizeHandler() {
        sessionIds = new ArrayList<>();
        loginsBySessionIds = new HashMap<>();
    }

    private String extractSessionIdFromHttpRequest(HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession();
        return httpSession.getId();
    }

    public boolean isAuthorized(HttpServletRequest httpServletRequest) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        return sessionIds.contains(sessionId);
    }

    private void addCookieFromHttpSession(HttpServletRequest httpServletRequest, String sessionId) {
        sessionIds.add(sessionId);
    }

    private void removeCookieFromHttpSession(HttpServletRequest httpServletRequest, String sessionId) {
        sessionIds.remove(sessionId);
    }

    public void newAuthorized(HttpServletRequest httpServletRequest, String login) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        addCookieFromHttpSession(httpServletRequest, sessionId);
        if (loginsBySessionIds.containsKey(sessionId)) {
            return;
        }
        loginsBySessionIds.put(sessionId, login);
    }

    public void logout(HttpServletRequest httpServletRequest) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        removeCookieFromHttpSession(httpServletRequest, sessionId);
        if (!loginsBySessionIds.containsKey(sessionId)) {
            return;
        }
        loginsBySessionIds.remove(sessionId);
    }
}
