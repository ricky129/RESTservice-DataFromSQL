package com.esempio.demo;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.hibernate.cfg.Configuration;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<Utente> getUserDetails() {
        IUtenteDAO utenteDAO = new UtenteDAOImpl(new Configuration().configure().buildSessionFactory());
        return utenteDAO.findAll();
    }
}