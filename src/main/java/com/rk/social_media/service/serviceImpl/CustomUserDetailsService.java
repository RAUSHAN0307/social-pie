package com.rk.social_media.service.serviceImpl;

import com.rk.social_media.entity.User;
import com.rk.social_media.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// isko implements karne jo spring by default password generate karke bhej raha tha wah ab nahi bhejega
// ab jo bhi user name aayega uska email or password get karke yeh bhejega agar email mila hi nahi to exception throw kar dega
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if(user == null) throw new UsernameNotFoundException("user not found with this email " + username);
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail() , user.getPassword() , authorities);
    }
}
