package org.piccolino.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.piccolino.entities.Order;
import org.piccolino.entities.Product;

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

    public void save(Order order){
        Transaction tx = null;

        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            session.persist(order);

            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }
    public Order getById(int id){
        Transaction tx = null;
        Order result = null;

        try{
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();

            result = (Order) session.createQuery("from Order o where o.id = :id")
                    .setParameter("id", id)
                    .getSingleResult();

            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if(tx != null){
                tx.rollback();
            }
        }
        return result;
    }

}
