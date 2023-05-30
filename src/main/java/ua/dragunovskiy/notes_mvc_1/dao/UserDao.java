package ua.dragunovskiy.notes_mvc_1.dao;

import java.util.List;

public interface UserDao<Integer, User> {
    List<User> getAll();
    User getById(int id);
    void add(User entity);
    void update(User entity);
    void deleteById(int id);
}
