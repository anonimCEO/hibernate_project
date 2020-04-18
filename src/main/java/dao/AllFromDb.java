package dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.List;

public interface AllFromDb <E>{

    default void toDatabase(List<E> list){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session s = null;
        Transaction t = null;

        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            for (E c : list)
                s.save(c);
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
    }

    default List<E> allFomDatabase(E e){
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session s = null;
        Transaction t = null;
        List<E> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("from "+e.getClass().getCanonicalName());
            list = query.list();
            t.commit();
        } catch (Exception ex) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }
}
