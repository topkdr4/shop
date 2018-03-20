package ru.vetoshkin.pojo;
import org.springframework.security.core.userdetails.User;

import java.util.Set;





/**
 * Ветошкин А.В. РИС-16бзу  
 * */
public class Role {
    private Long id;
    private String name;
    private Set<User> users;
    
    
    public Long getId() {
        return id;
    }
    
    
    public void setId(Long id) {
        this.id = id;
    }
    
    
    public String getName() {
        return name;
    }
    
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    public Set<User> getUsers() {
        return users;
    }
    
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
