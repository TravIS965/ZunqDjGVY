// 代码生成时间: 2025-10-15 21:27:52
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class GameResourceManager {
    
    private SessionFactory sessionFactory;
    
    // Constructor to initialize the SessionFactory
    public GameResourceManager() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    
    // Method to add a new game resource
    public boolean addGameResource(GameResource resource) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(resource);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to update an existing game resource
    public boolean updateGameResource(GameResource resource) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(resource);
            transaction.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to delete a game resource
    public boolean deleteGameResource(int resourceId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            GameResource resource = session.get(GameResource.class, resourceId);
            if (resource != null) {
                session.delete(resource);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // Method to retrieve a game resource by ID
    public GameResource getGameResourceById(int resourceId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(GameResource.class, resourceId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Method to retrieve all game resources
    public List<GameResource> getAllGameResources() {
        try (Session session = sessionFactory.openSession()) {
            Query<GameResource> query = session.createQuery("FROM GameResource", GameResource.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        GameResourceManager manager = new GameResourceManager();
        // Add a new game resource
        GameResource resource = new GameResource(1, "Resource1", "Description1");
        manager.addGameResource(resource);
        
        // Update the game resource
        resource.setDescription("Updated Description");
        manager.updateGameResource(resource);
        
        // Retrieve and print the game resource
        GameResource retrievedResource = manager.getGameResourceById(1);
        System.out.println("Retrieved Resource: " + retrievedResource);
        
        // Delete the game resource
        manager.deleteGameResource(1);
    }
}

/**
 * GameResource.java
 * 
 * This class represents a game resource entity with its properties and behavior.
 * 
 * @author Your Name
 * @version 1.0
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class GameResource {
    @Id
    private int id;
    @Column
    private String name;
    @Column
    private String description;
    
    public GameResource() {
    }
    
    public GameResource(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "GameResource{id=" + id + ", name='" + name + "', description='" + description + "'}";
    }
}