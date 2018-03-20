package ru.vetoshkin.service;
import ru.vetoshkin.pojo.User;





public interface UserService {
    
    public void save(User user);
    
    public User findByUserName(String userName);
    
    
}
