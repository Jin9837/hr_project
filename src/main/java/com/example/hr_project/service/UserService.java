package com.example.hr_project.service;

import com.example.hr_project.dao.UserDao;
import com.example.hr_project.domain.entity.User;
import com.example.hr_project.exception.InvalidCredentialsException;
import com.example.hr_project.security.AuthUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@EnableTransactionManagement
public class UserService implements UserDetailsService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

//    @Transactional
//    public void addUser(User user){
//        userDao.addUser(user);
//    }

    @Transactional
    public User login(String username, String password) throws InvalidCredentialsException {
        return userDao.login(username, password);
    }



    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByUsername(username);

        return AuthUserDetail.builder() // spring security's userDetail
                .username(user.getUsername())
                .password(new BCryptPasswordEncoder().encode(user.getPassword()))
                .authorities(getAuthoritiesFromUser(user))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }


//    @Transactional
//    public User getUserById(int userId) {
//        User user = userDao.getUserById(userId);
//        return user;
//    }
//
//    @Transactional
//    @Async("taskExecutor")
//    public CompletableFuture<User> getUserByIdAsync(int userId)
//    {
//        User user = userDao.getUserById(userId);
//        return CompletableFuture.completedFuture(user);
//    }



    private List<GrantedAuthority> getAuthoritiesFromUser(User user){
        List<GrantedAuthority> userAuthorities = new ArrayList<>();
        userAuthorities.add(new SimpleGrantedAuthority(user.getUserRole()));


        return userAuthorities;
    }
}
