package com.comtrade.security;


import com.comtrade.model.user.User;
import com.comtrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    MyUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;
        this.userRepository.save(new User("user1","$2a$10$Whcr6HD8FI6/TsaH0nqyMuF8nEYkw987FBkcraT3wLBPSJlnE5/te",true,"ROLE_USER"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByUserName(username);
        user.orElseThrow(()-> new UsernameNotFoundException("Not found: "+username));
        return user.map(MyUserDetails::new).get();
    }

    public String addUser(String userName, String password) {
        Optional<User> user=userRepository.findByUserName(userName);
        if(user.isEmpty()) {
            this.userRepository.save(new User(userName, bCryptPasswordEncoder.encode(password), true, "ROLE_USER"));
            return "Uspesno ste se registrovali";
        }else{
            return "Zauseto korisnicko ime";
        }
    }
}
