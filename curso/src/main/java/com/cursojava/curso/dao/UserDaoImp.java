package com.cursojava.curso.dao;

import com.cursojava.curso.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository //funcionalidad de acceso a repositioro de la base de datos
@Transactional//consultas SQL
public class UserDaoImp implements UserDao{
    @PersistenceContext
    EntityManager entityManager;//conexion con BD

    @Override
    public List<User> getUsers() {
        String query = "FROM User";
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void eliminar(Long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void registerUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User getUserByCredentials(User user) {
        String query = "FROM User WHERE email = :email";
        List<User> lista = entityManager.createQuery(query)
            .setParameter("email", user.getEmail())
            .getResultList();

        if (lista.isEmpty() ){
            return null;
        }
        String hashedPassword = lista.get(0).getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);

        if(argon2.verify(hashedPassword, user.getPassword())){
            return lista.get(0); //no me convece
        }

        return null;

    }
}
