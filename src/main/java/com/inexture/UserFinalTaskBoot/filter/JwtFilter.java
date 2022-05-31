package com.inexture.UserFinalTaskBoot.filter;

import com.inexture.UserFinalTaskBoot.Services.CustomUserDetailsService;
import com.inexture.UserFinalTaskBoot.Utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("inside filter");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    String jwt = cookie.getValue();
                    String email = jwtUtil.extractUsername(jwt);
                    if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
                        if(jwtUtil.validateToken(jwt,userDetails)){
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());

                            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                            request.setAttribute("email",email);
                            System.out.println(email);
                        }else{
                            response.sendRedirect("indexView");
                        }
                    }
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
