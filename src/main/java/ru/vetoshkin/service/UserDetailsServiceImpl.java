package ru.vetoshkin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vetoshkin.dao.UserDao;
import ru.vetoshkin.pojo.Role;
import ru.vetoshkin.pojo.User;

import java.util.HashSet;
import java.util.Set;


/**
 * Ветошкин А.В. РИС-16бзу
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("UserDetailsServiceImpl#loadUserByUsername");

        User user = userDao.findByUserName(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        for (Role role : user.getRoles()) {
            SimpleGrantedAuthority a = new SimpleGrantedAuthority(role.getName());
            System.out.println(a);
            grantedAuthorities.add(a);
        }

        System.out.println(user);
        UserDetails result = new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);
        System.out.println(result.getPassword());
        return result;
    }
}
