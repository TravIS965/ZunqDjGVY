// 代码生成时间: 2025-10-14 21:45:54
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.ArrayList;

public class EdgeComputingFramework {

    // Hibernate Session Factory
    private static SessionFactory factory;

    static {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to add a new edge node
    public static void addEdgeNode(EdgeNode node) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(node);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to update an existing edge node
    public static void updateEdgeNode(EdgeNode node) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(node);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to delete an edge node
    public static void deleteEdgeNode(int id) {
        Session session = factory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            EdgeNode node = session.get(EdgeNode.class, id);
            if (node != null) {
                session.delete(node);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Method to retrieve all edge nodes
    public static List<EdgeNode> getAllEdgeNodes() {
        List<EdgeNode> nodes = new ArrayList<>();
        Session session = factory.openSession();
        try {
            Query<EdgeNode> query = session.createQuery("FROM EdgeNode", EdgeNode.class);
            nodes = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return nodes;
    }

    // Method to retrieve a single edge node by ID
    public static EdgeNode getEdgeNodeById(int id) {
        Session session = factory.openSession();
        EdgeNode node = null;
        try {
            node = session.get(EdgeNode.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return node;
    }
}

/*
 * EdgeNode.java
 *
 * Represents an edge node in the edge computing framework.
 *
 * @author Your Name
 * @version 1.0
 */
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Objects;

@Entity
public class EdgeNode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String ipAddress;

    // Constructors
    public EdgeNode() {
    }

    public EdgeNode(String name, String ipAddress) {
        this.name = name;
        this.ipAddress = ipAddress;
    }

    // Getters and setters
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    // Override equals and hashCode for proper comparison
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EdgeNode edgeNode = (EdgeNode) o;
        return id == edgeNode.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
