// 代码生成时间: 2025-09-22 13:53:36
package com.example.userloginsystem;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;

// User entity class representing the user data
class User {
    private int id;
    private String username;
    private String password;

    // Constructor, getters and setters
    public User() {}
    public User(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

// DAO class for user operations
class UserDao {
    private SessionFactory sessionFactory;

    public UserDao() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public User authenticate(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            List<User> users = query.getResultList();
            if (users.isEmpty()) {
                return null;
            } else {
                return users.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

// Main class to run the program
public class UserLoginSystem {
    public static void main(String[] args) {
        UserDao userDao = new UserDao();
        String username = "admin";
        String password = "password123";
        User user = userDao.authenticate(username, password);
        if (user != null) {
            System.out.println("Login successful for user: " + user.getUsername());
        } else {
            System.out.println("Invalid username or password");
        }
    }
}
