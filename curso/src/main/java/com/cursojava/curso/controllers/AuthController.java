package com.cursojava.curso.controllers;

import com.cursojava.curso.Utils.JWTUtil;
import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/")
public class AuthController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JWTUtil jwtUtil;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@RequestBody User user){ // esta notacion genera el objeto a partir del JSON
        User verifideUser = userDao.getUserByCredentials(user);
        if (verifideUser != null){
            String token = jwtUtil.create(String.valueOf(verifideUser.getId()), verifideUser.getEmail()); //genero el token que almacena el cliente
            return token;
        }
        return "FAIL";
    }
}
