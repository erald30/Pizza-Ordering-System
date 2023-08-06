package org.piccolino;

import org.hibernate.SessionFactory;
import org.piccolino.utilities.DbConnection;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = DbConnection.getFACTORY();
    }
}
