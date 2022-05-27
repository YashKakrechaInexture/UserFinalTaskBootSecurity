package com.inexture.UserFinalTaskBoot;

import com.inexture.UserFinalTaskBoot.Services.CustomUserDetailsService;
import com.inexture.UserFinalTaskBoot.Utilities.ShaEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/index","/loginServlet","/lib/**").permitAll()
                .antMatchers("/homepage").hasRole("user")
                .antMatchers("/adminServlet").hasRole("admin")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/index")
                .usernameParameter("email")
                .passwordParameter("password")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/homepage").permitAll();

    }

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
    }

    public PasswordEncoder passwordEncoder(){
        return ShaEncryption.getInstance();
    }
}
