package com.cursojava.curso.dao;

import com.cursojava.curso.models.User;

import java.util.List;

public interface UserDao {
    List <User> getUsers();

    void eliminar(Long id);

    void registerUser(User user);

    User getUserByCredentials(User user);
}
