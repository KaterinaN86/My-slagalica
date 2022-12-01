package com.comtrade.security;


import com.comtrade.model.user.User;
import com.comtrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    MyUserDetailService(UserRepository userRepository){
        this.userRepository=userRepository;
        this.userRepository.save(new User("user1","pass",true,"ROLE_USER"));
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
            this.userRepository.save(new User(userName, password, true, "ROLE_USER"));
            return "Uspesno ste se registrovali";
        }else{
            return "Zauseto korisnicko ime";
        }
    }
}
