package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory factory;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        factory = Util.getSessionFactory();
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("create table if not exists Users  (\n" +
                            "ID       int unsigned auto_increment,\n" +
                            "NAME     varchar(32) not null,\n" +
                            "LASTNAME varchar(64) not null,\n" +
                            "AGE      tinyint unsigned not null,\n" +
                            "primary key (ID))")
                    .executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        factory = Util.getSessionFactory();
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table Users").executeUpdate();
            session.getTransaction().commit();
        } catch (PersistenceException e) {

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        factory = Util.getSessionFactory();
        User user = new User(name, lastName, age);
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        factory = Util.getSessionFactory();
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = null;
        factory = Util.getSessionFactory();
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            list = session.createQuery("from User").list();
            session.getTransaction().commit();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        factory = Util.getSessionFactory();
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
