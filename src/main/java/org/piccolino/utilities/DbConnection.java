package org.piccolino.utilities;

import org.piccolino.entities.OrderItem;
import org.piccolino.entities.Order;
import org.piccolino.entities.Product;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class DbConnection {
    private static SessionFactory FACTORY = null;

    public static SessionFactory getFACTORY(){
        if (FACTORY == null){
            synchronized (SessionFactory.class){
                if(FACTORY == null){
                    try{
                        Properties prop = new Properties();
                        prop.setProperty(Environment.URL,"jdbc:mysql://localhost:3306/piccolino");
                        prop.setProperty(Environment.HBM2DDL_AUTO, "update");
                        prop.setProperty(Environment.SHOW_SQL, "false");
                        prop.setProperty(Environment.FORMAT_SQL, "true");
                        prop.setProperty(Environment.USER, System.getenv("MYSQL_USER"));
                        prop.setProperty(Environment.PASS, System.getenv("MYSQL_PSWD"));
                        prop.setProperty(Environment.DIALECT , "org.hibernate.dialect.MySQLDialect");
                        prop.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                        prop.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                        Configuration config = new Configuration();
                        config.setProperties(prop);
                        config.addPackage("entities");
                        config.addAnnotatedClass(Order.class);
                        config.addAnnotatedClass(Product.class);
                        config.addAnnotatedClass(OrderItem.class);


                        ServiceRegistry serviceRegistry =  new StandardServiceRegistryBuilder()
                                .applySettings(config.getProperties()).build();

                        FACTORY = config.buildSessionFactory(serviceRegistry);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return FACTORY;
    }
}
