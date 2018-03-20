package ru.vetoshkin.dao;





import ru.vetoshkin.pojo.User;





public interface UserDao {
    
    public User findByUserName(String userName);
    
}
