package com.inexture.UserFinalTaskBoot;

import com.inexture.UserFinalTaskBoot.Beans.CustomUserDetails;
import com.inexture.UserFinalTaskBoot.Services.CustomUserDetailsService;
import com.inexture.UserFinalTaskBoot.Utilities.JwtUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

public class MySimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    static final Logger LOG = Logger.getLogger(MySimpleUrlAuthenticationSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        LOG.debug("Inside authentication success handler.");

        String targetUrl = null;
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println(authentication.getCredentials());
        System.out.println(authentication.getDetails());
        System.out.println(authentication.getName());
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(authorityName!=null && authorityName.equals("user")) {
                LOG.debug("Role is user, redirecting to homepage.");
                targetUrl = "/homepage";
            }
            if(authorityName!=null && authorityName.equals("admin")) {
                LOG.debug("Role is admin, redirecting to adminServlet.");
                targetUrl = "/adminServlet";
            }
        }
//        request.setAttribute("email",authentication.getName());

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(authentication.getName());

        String jwt = jwtUtil.generateToken(userDetails);

        Cookie jwtTokenCookie = new Cookie("jwt",jwt);
        response.addCookie(jwtTokenCookie);
        LOG.debug("Cookie stored");

        redirectStrategy.sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(request);
    }


    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
