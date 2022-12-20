package com.comtrade.security;

import com.comtrade.model.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private final String userName;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final Boolean active;

    public MyUserDetails(User user){
        this.userName=user.getUserName();
        this.password=user.getPassword();
        this.active=user.isActive();
        this.authorities=Arrays.stream(user.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
    public MyUserDetails(String userName){
        this.userName=userName;
        this.password="pass";
        this.active=true;
        this.authorities= List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return active;
    }
}
