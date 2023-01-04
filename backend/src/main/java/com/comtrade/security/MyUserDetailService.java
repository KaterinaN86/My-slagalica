package com.comtrade.security;


import com.comtrade.model.user.User;
import com.comtrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    MyUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByUserName(username);
        user.orElseThrow(()-> new UsernameNotFoundException("Not found: "+username));
        return user.map(MyUserDetails::new).get();
    }

    public String addUser(String userName, String password) {
        if (userName==""||password.length()<5){
            return "Bad username or password";
        }
        Optional<User> user=userRepository.findByUserName(userName);

        if(user.isEmpty()) {
            this.userRepository.save(new User(userName, bCryptPasswordEncoder.encode(password), true, "ROLE_USER"));
            return "You successfully registered";
        }else{
            return "Username already taken";
        }
    }
}
