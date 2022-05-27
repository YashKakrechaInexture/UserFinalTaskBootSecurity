package com.inexture.UserFinalTaskBoot.Beans;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private UserBean userBean;

    public CustomUserDetails(UserBean userBean){
        this.userBean = userBean;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = userBean.getType();
        Set<SimpleGrantedAuthority> authoritySet =  new HashSet<>();
        authoritySet.add(new SimpleGrantedAuthority(role));
        return authoritySet;
    }

    @Override
    public String getPassword() {
        return this.userBean.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userBean.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
