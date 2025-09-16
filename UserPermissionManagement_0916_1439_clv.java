// 代码生成时间: 2025-09-16 14:39:03
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;

// 用户权限实体类
class Permission {
    private int id;
    private String name;
    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

// 用户实体类
class User {
    private int id;
    private String name;
    private List<Permission> permissions = new ArrayList<>();
    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public List<Permission> getPermissions() { return permissions; }
    public void setPermissions(List<Permission> permissions) { this.permissions = permissions; }
}

// 用户权限管理类
public class UserPermissionManagement {

    public static void main(String[] args) {
        try (Session session = new Configuration().configure().buildSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                User user = new User();
                user.setId(1);
                user.setName("Alice");

                Permission permission = new Permission();
                permission.setId(1);
                permission.setName("admin");
                user.getPermissions().add(permission);

                session.save(user);
                session.save(permission);
                transaction.commit();
                System.out.println("User and permission saved successfully.");
            } catch (Exception e) {
                transaction.rollback();
                System.err.println("Error occurred: " + e.getMessage());
            }
        }
    }

    // Method to add a new permission to a user
    public void addPermission(int userId, int permissionId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = new Configuration().configure().buildSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            Permission permission = session.get(Permission.class, permissionId);
            user.getPermissions().add(permission);
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error adding permission: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    // Method to remove a permission from a user
    public void removePermission(int userId, int permissionId) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = new Configuration().configure().buildSessionFactory().openSession();
            transaction = session.beginTransaction();
            User user = session.get(User.class, userId);
            Permission permission = session.get(Permission.class, permissionId);
            user.getPermissions().remove(permission);
            session.update(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Error removing permission: " + e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    // Additional methods can be added for other functionalities, such as listing permissions,
    // updating user details, etc.
}
