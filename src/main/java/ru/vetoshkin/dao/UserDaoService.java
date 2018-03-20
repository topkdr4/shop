package ru.vetoshkin.dao;

import org.springframework.stereotype.Service;
import ru.vetoshkin.pojo.Role;
import ru.vetoshkin.pojo.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDaoService implements UserDao {

    @Override
    public User findByUserName(String userName) {
        User user = new User();
        user.setLogin("admin");
        user.setPassword("acef13da09");
        user.setEncodedPassword("02619d647719708f99ebcf7b15d33b14751e048ead936b074e0d02e56c9f0fe4"); //acef13da09

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(adminRole);

        user.setRoles(roleSet);
        System.out.println("UserDaoService#findByUserName");
        return user;
    }

}
