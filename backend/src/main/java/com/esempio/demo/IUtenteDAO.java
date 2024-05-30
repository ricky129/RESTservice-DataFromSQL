package com.esempio.demo;

import com.esempio.demo.Utente;
import java.util.List;

public interface IUtenteDAO {
    Utente findById(int id);
    void save(Utente utente);
    void update(Utente utente);
    void delete(Utente utente);
    List<Utente> findAll();
}