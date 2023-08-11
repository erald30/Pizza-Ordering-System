package org.piccolino.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.piccolino.entities.Order;
import org.piccolino.entities.OrderItem;
import org.piccolino.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderItemRepository {

    private SessionFactory sessionFactory;

    public OrderItemRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<OrderItem> getAllOrderItems(){
        Transaction tx = null;
        List<OrderItem> result = new ArrayList<>();

        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.createQuery("from OrderItem").getResultList();

            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return result;

    }

    public void save(OrderItem orderItem){
        Transaction tx = null;

        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.persist(orderItem);

            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}

