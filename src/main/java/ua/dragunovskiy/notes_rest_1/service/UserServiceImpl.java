package ua.dragunovskiy.notes_rest_1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dragunovskiy.notes_rest_1.dao.UserDaoImpl;
import ua.dragunovskiy.notes_rest_1.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService<Integer, User> {

    @Autowired
    UserDaoImpl userDao;

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User getById(int id) {
       return userDao.getById(id);
    }

    @Override
    public void add(User entity) {
        userDao.add(entity);
    }

    @Override
    public void update(User entity) {
        userDao.update(entity);
    }

    @Override
    public void deleteById(int id) {
        userDao.deleteById(id);
    }
}
