package ru.vetoshkin.pojo;
import java.util.Set;





/**
 * Ветошкин А.В. РИС-16бзу  
 * */

public class User {
    private String login;
    private String password;
    private String encodedPassword;
    private Set<Role> roles;
    
    
    public String getLogin() {
        return login;
    }
    
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    
    public String getPassword() {
        return password;
    }
    
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    
    public Set<Role> getRoles() {
        return roles;
    }


    public String getEncodedPassword() {
        return encodedPassword;
    }

    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
