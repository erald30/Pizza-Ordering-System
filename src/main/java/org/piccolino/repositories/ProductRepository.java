package org.piccolino.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.piccolino.entities.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
    private SessionFactory sessionFactory;

    public ProductRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

        public List<Product> getAllProducts(){
            Transaction tx = null;
            List<Product> result = new ArrayList<>();

            try {
                Session session = sessionFactory.openSession();
                tx = session.beginTransaction();

                session.createQuery("from Product").getResultList();

                tx.commit();
            } catch (Exception ex) {
                ex.printStackTrace();
                if (tx != null) {
                    tx.rollback();
                }
            }
            return result;

        }
    }

