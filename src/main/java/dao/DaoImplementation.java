package dao;

import entities.Discipline;
import entities.Role;
import entities.Task;
import entities.User;
import enums.Status;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtils;

import java.util.*;

public class DaoImplementation implements AllFromDb{

    private static SessionFactory sessionFactory = HibernateUtils.getSessionFactory();


    public List<User> getUsersByRole(String role){
        Session s = null;
        Transaction t = null;
        List<User> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("SELECT u FROM User u INNER JOIN u.roles r WHERE r.name =:role");
            query.setString("role", role);
            list = query.list();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }

    public List<User> userListByDiscipline(String discipline){
        Session s = null;
        Transaction t = null;
        List<User> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("SELECT u FROM User u INNER JOIN u.discipline d WHERE d.name =:discipline");
            query.setString("discipline", discipline);
            list = query.list();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }

    public List<User> userListByTaskStatus(String taskStatus){
        Session s = null;
        Transaction t = null;
        List<User> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("SELECT u FROM User u JOIN u.tasks t WHERE t.status =:taskStatus");
            query.setString("taskStatus", taskStatus);
            list = query.list();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }


    public List<Discipline> disciplineListLessThan(int number){
        Session s = null;
        Transaction t = null;
        List<Discipline> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();

            Query query  = s.createQuery("SELECT d FROM Discipline d INNER JOIN d.members as m GROUP BY d.id having count (m.id)< :number");
            query.setInteger("number", number);
            list = query.list();
            t.commit();
        } catch (Exception e) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }

    public void changeDisciplineHead(Discipline discipline, User user) {
        Transaction t = null;
        try (Session s = sessionFactory.openSession()) {
            t = s.beginTransaction();
            discipline.setHeadOfDiscipline(user);
            s.update(discipline);
            t.commit();
        } catch (Exception e) {
            if (t != null)
                t.rollback();
        }
    }

    public void deleteObject(User user){
        Session s = null;
        try {
            s = sessionFactory.openSession();
            s.beginTransaction();
            user.setEnabled(false);
            s.update(user);
//            s.delete(user);
            s.getTransaction().commit();
        } catch (Exception e) {
            s.getTransaction().rollback();
        } finally {
            if (s != null)
                s.close();
        }
    }

    public static List<Task> taskFactory(List<User> user){
        List<Task> list = new ArrayList();

        list.add(new Task("Java","make a website", new Date(), new Date(), Status.TO_DO, user.get(5)));
        list.add(new Task("Python","make a website", new Date(), new Date(), Status.TO_DO, user.get(0)));
        list.add(new Task("PHP","make a website", new Date(), new Date(), Status.IN_PROGRESS, user.get(2)));
        list.add(new Task("Ruby","make a website", new Date(), new Date(), Status.TO_DO, user.get(4)));
        list.add(new Task(".Net","make a website", new Date(), new Date(), Status.IN_REVIEW, user.get(3)));
        return list;
    }

    public static List<Discipline> disciplineFactory(List<User> members, List<User> headOfDiscipline){
        List<Discipline> list = new ArrayList();
        list.add(new Discipline("Applications Management", new HashSet<User>(Arrays.asList(members.get(0))),headOfDiscipline.get(0)));
        list.add(new Discipline("Development", new HashSet<User>(Arrays.asList(members.get(1))),headOfDiscipline.get(1)));
        list.add(new Discipline("Testing", new HashSet<User>(Arrays.asList(members.get(2))),headOfDiscipline.get(2)));
        list.add(new Discipline("Architecture", new HashSet<User>(Arrays.asList(members.get(4))),headOfDiscipline.get(3)));
        list.add(new Discipline("Analyst", new HashSet<User>(Arrays.asList(members.get(5))),headOfDiscipline.get(3)));
        list.add(new Discipline("Marketing", new HashSet<User>(Arrays.asList(members.get(6))),headOfDiscipline.get(3)));
        list.add(new Discipline("Data", new HashSet<User>(Arrays.asList(members.get(1),members.get(3), members.get(5))),headOfDiscipline.get(3)));


        return list;
    }


    public static List<Role> roleFactory(){
        List<Role> list = new ArrayList();
        list.add(new Role("Developer"));
        list.add(new Role("Tester"));

        return list;
    }


    public static List<User> userFactory(List<Role> roles) {
        List<User> list = new ArrayList();
        list.add(new User("Nicolae", "Stropsa","nstropsa@endava.com","nstropsa",
                new Date(), true,
                new HashSet<Role>(Arrays.asList(roles.get(1)))));

        list.add(new User("Dan", "Sajin","dsajin@endava.com","dsajin",
                new Date(), true,
                new HashSet<Role>(Arrays.asList(roles.get(1)))));

        list.add(new User("Iurii", "Luncasu","iluncasu@endava.com","iluncasu",
                new Date(), true,
                new HashSet<Role>(Arrays.asList(roles.get(0)))));

        list.add(new User("Vladimir", "Cucu","vcucu@endava.com","vcucu",
                new Date(), true,
                new HashSet<Role>(Arrays.asList(roles.get(0)))));

        list.add(new User("Ivan", "Doe","idoe@endava.com","idoe",
                new Date(), true,
                new HashSet<Role>(Arrays.asList(roles.get(0)))));

        list.add(new User("Johny", "Deep","jdeep@endava.com","jdeep",
                new Date(), true,
                new HashSet<Role>(Arrays.asList(roles.get(1)))));

        list.add(new User("Mike", "Spike","mspike@endava.com","mspike",
                new Date(), true,
                new HashSet<Role>(Arrays.asList(roles.get(0)))));

        return list;
    }
}
