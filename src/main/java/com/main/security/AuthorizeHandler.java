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

    private void addCookieFromHttpSession(HttpServletRequest httpServletRequest, String sessionId) {
        sessionIds.add(sessionId);
    }

    private void removeCookieFromHttpSession(HttpServletRequest httpServletRequest, String sessionId) {
        sessionIds.remove(sessionId);
    }

    public boolean isAuthorized(HttpServletRequest httpServletRequest) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        return sessionIds.contains(sessionId);
    }

    public void newAuth(HttpServletRequest httpServletRequest, String login) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        if (loginsBySessionIds.containsKey(sessionId)) {
            return;
        }
        addCookieFromHttpSession(httpServletRequest, sessionId);
        loginsBySessionIds.put(sessionId, login);
    }

    public void logout(HttpServletRequest httpServletRequest) {
        final String sessionId = extractSessionIdFromHttpRequest(httpServletRequest);
        if (!loginsBySessionIds.containsKey(sessionId)) {
            return;
        }
        removeCookieFromHttpSession(httpServletRequest, sessionId);
        loginsBySessionIds.remove(sessionId);
    }
}
