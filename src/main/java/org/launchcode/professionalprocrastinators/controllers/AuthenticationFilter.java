package org.launchcode.professionalprocrastinators.controllers;

import jakarta.servlet.http.*;
import org.launchcode.professionalprocrastinators.models.*;
import org.launchcode.professionalprocrastinators.models.data.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.servlet.*;

import java.io.*;
import java.util.*;

public class AuthenticationFilter implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserAuthentication userAuthentication;

//the acceptable pages that can be viewed even if not logged in
    private static final List<String> whitelist = Arrays.asList("/login", "/account_deleted", "/register", "/contact");

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {

// Don't require sign-in for whitelisted pages
        if (isWhitelisted(request.getRequestURI())) {
// returning true indicates that the request may proceed
            return true;
        }

        HttpSession session = request.getSession();
        User user = userAuthentication.getUserFromSession(session);

// The user is logged in
        if (user != null) {
            return true;
        }

// The user is NOT logged in
        response.sendRedirect("/login");
        return false;
    }
    private static boolean isWhitelisted(String path) {
        for (String pathRoot : whitelist) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }
}
