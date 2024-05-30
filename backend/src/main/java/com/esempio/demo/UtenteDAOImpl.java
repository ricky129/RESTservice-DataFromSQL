package com.esempio.demo;

import com.esempio.demo.Utente;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class UtenteDAOImpl implements IUtenteDAO {
    private SessionFactory sessionFactory;

    public UtenteDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Utente findById(int id) {
        Session session = sessionFactory.openSession();
        Utente utente = session.get(Utente.class, id);
        session.close();
        return utente;
    }

    @Override
    public void save(Utente utente) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.save(utente);
        tx.commit();
        session.close();
    }

    @Override
    public void update(Utente utente) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.update(utente);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Utente utente) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.delete(utente);
        tx.commit();
        session.close();
    }

    @Override
    public List<Utente> findAll() {
        Session session = sessionFactory.openSession();
        List<Utente> list = session.createQuery("FROM Utente", Utente.class).list();
        session.close();
        return list;
    }
}