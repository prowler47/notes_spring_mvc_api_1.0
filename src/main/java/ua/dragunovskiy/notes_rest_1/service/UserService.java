package ua.dragunovskiy.notes_rest_1.service;

import java.util.List;

public interface UserService<Integer, User> {
    List<User> getAll();
    User getById(int id);
    void add(User entity);
    void update(User entity);
    void deleteById(int id);
}
