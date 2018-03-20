package ru.vetoshkin.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.vetoshkin.dao.RoleDao;
import ru.vetoshkin.dao.UserDao;
import ru.vetoshkin.pojo.Role;
import ru.vetoshkin.pojo.User;

import java.util.HashSet;
import java.util.Set;





/**
 * Ветошкин А.В. РИС-16бзу  
 * */
@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserDao userDao;
    
    @Autowired
    private RoleDao roleDao;
    
    /*@Autowired
    private PasswordEncoder passwordEncoder;*/


    @Override
    public void save(User user) {
        System.out.println(user);
      //  user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        user.setRoles(roles);
        System.out.println("UserServiceImpl#save");
    }
    
    
    @Override
    public User findByUserName(String userName) {
        System.out.println("UserServiceImpl#findByUserName");
        return userDao.findByUserName(userName);
    }
    
}
