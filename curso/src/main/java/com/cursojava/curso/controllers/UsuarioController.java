package com.cursojava.curso.controllers;

import com.cursojava.curso.Utils.JWTUtil;
import com.cursojava.curso.dao.UserDao;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import com.cursojava.curso.models.User;
@RestController
@RequestMapping("api/")
public class UsuarioController {
    @Autowired //genera un objeto UserDaoImp y se lo asigna a la variable de abajo
    private UserDao userDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "users", method = RequestMethod.GET)
    public List<User> getUsers(@RequestHeader (value = "Authorization") String token){

        if (!validateToke(token)){ //devuelve true si es valido
            return null;
        }
        return userDao.getUsers();
    }

    @RequestMapping(value = "users", method = RequestMethod.POST)
    public void registerUser(@RequestBody User user){ // esta notacion genera el objeto a partir del JSON
        //hashing de contraseñas
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, user.getPassword());//iteraciones, espacion en mem(?), theads creados, informacion a hashear
        user.setPassword(hash); // almacenamos el hash
        userDao.registerUser(user);
    }

    @RequestMapping(value = "userx")
    public User editUser(){
        User user = new User();
        user.setNombre("Pedro");
        user.setApellido("Molina");
        user.setEmail("pepe@gmail.com");
        user.setTelefono("23232323");
        user.setPassword("1234");
        return user;
    }

    @RequestMapping(value = "users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@RequestHeader (value = "Authorization") String token, @PathVariable Long id) {
        if (!validateToke(token)){ //devuelve true si es valido
            return;
        }
        userDao.eliminar(id);
;    }

    private boolean validateToke(String token){
        String userId = jwtUtil.getKey(token);
        return userId != null;
    }
}
