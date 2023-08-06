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

        public Product getByTitle(String title) {
                    Transaction tx = null;
                    Product result = null;

                    try {
                        Session session = sessionFactory.openSession();
                        tx = session.beginTransaction();

                        List<Product> tmpResult = session.createQuery("from Product p where lower(p.title)  = lower(:title)  ")
                                .setParameter("title", title)
                                .getResultList();



                        result = tmpResult.size() > 0 ? tmpResult.get(0) : null;
                        // Same as
                        /*if (tmpResult.size() > 0)
                            result = tmpResult.get(0);
                        else
                            result = null;*/

                        tx.commit();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        if (tx != null) {
                            tx.rollback();
                        }
                    }
                    return result;
        }

    public void save(Product model) {
        Transaction tx = null;

        try {
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();

            session.persist(model);

            tx.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }
}

