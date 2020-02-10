package ir.maktab.core.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    private static Session session;

    private HibernateUtil() {
    }

    public static Session getSession(){
        if(session==null){
            session=sessionFactory.openSession();
            return session;
        }
        return session;
    }
}
