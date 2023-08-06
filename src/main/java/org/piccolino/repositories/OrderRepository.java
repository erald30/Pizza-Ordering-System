package org.piccolino.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.piccolino.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {

    private SessionFactory sessionFactory;

    public OrderRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public List<Order> getOrders(){
        Transaction tx = null;
        List<Order> result = new ArrayList<>();

        try{
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.createQuery("from Order").getResultList();

            tx.commit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return result;
    }
}
