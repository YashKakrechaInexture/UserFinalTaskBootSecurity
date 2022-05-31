package com.inexture.UserFinalTaskBoot;

import com.inexture.UserFinalTaskBoot.Services.CustomUserDetailsService;
import com.inexture.UserFinalTaskBoot.Utilities.ShaEncryption;
import com.inexture.UserFinalTaskBoot.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/","/index","/lib/**","/register","/resetPassword","/temp","/newPassword","/authEmailServlet","/newPasswordServlet","/resetPasswordServlet","/registerServlet","/token","/login","/logoutServlet").permitAll()
                .antMatchers("/adminServlet","/deleteServlet","/editServlet","/updateServlet").permitAll()   //.hasAuthority("admin")
                .antMatchers("/homepage").permitAll()   //.hasAuthority("user")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//                .and()
//                .formLogin()
//                .loginPage("/index")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .loginProcessingUrl("/login")
////                .defaultSuccessUrl("/adminServlet").permitAll()
//                .successHandler(myAuthenticationSuccessHandler())
//                .and()
//                .logout()
//                .logoutUrl("/logoutServlet")
//                .logoutSuccessUrl("/index");
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

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
