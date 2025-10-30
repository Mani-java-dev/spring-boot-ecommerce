package com.project.hammer.config;

import com.project.hammer.exceptions.BadRequestCustomException;
import com.project.hammer.serviceimpl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Autowired
    private JwtUtility jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestTokenHeader = request.getHeader("Authorization");

        String token = null;
        String userName = null;

        RateLimitConfiguration.performRateLimiting(request, SecurityConfig.WHITELISTENDPOINTS);

        if (Objects.nonNull(requestTokenHeader) && requestTokenHeader.startsWith("Bearer")) {

            token = requestTokenHeader.substring(7);

            try {
                userName = jwtUtil.extractUsername(token);
            } catch (IllegalArgumentException e) {
                log.warn("Unable to get JWT Token");
            }

        } else {
            log.info("token not found");
        }

        // Once we get the token, validate it
        if (Objects.nonNull(userName) && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            log.info(userDetails.toString());
            // if token is valid configure Spring Security to manually set authentication
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {

                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // After setting the Authentication in the context, we specify that the current user is authenticated
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


                log.info(SecurityContextHolder.getContext().toString());
            }
        }

        //passing request and response to antoher filter or controller
        filterChain.doFilter(request, response);

    }


}
