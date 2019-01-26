package com.adidas.subscriptionservice.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by mathias on 26/01/19.
 */

@Component
@Order(1)
public class AuthFilter implements Filter {


    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final int UNAUTHORIZED = 401;

    @Value("${jwtAuthToken}")
    private String authToken;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (request.getHeader(AUTHORIZATION_HEADER) == null || request.getHeader(AUTHORIZATION_HEADER).isEmpty()) {
            response.setStatus(UNAUTHORIZED);
            response.sendError(UNAUTHORIZED, "No token was specified in the Authentication header");
        } else {
            if (!request.getHeader(AUTHORIZATION_HEADER).equals(authToken)) {
                response.setStatus(UNAUTHORIZED);
                response.sendError(UNAUTHORIZED, "Invalid token");
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
