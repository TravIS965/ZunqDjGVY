// 代码生成时间: 2025-10-04 02:01:32
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;
import java.util.Scanner;

public class AchievementSystem {
    // Static block to initialize the EntityManagerFactory
    private static EntityManagerFactory emf;
    static {
        try {
            emf = Persistence.createEntityManagerFactory("AchievementPU");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    // Method to add a new achievement
    public void addAchievement(String achievementName) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Achievement achievement = new Achievement(achievementName);
            em.persist(achievement);
            em.getTransaction().commit();
            System.out.println("Achievement added: " + achievementName);
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    // Method to retrieve all achievements
    public List<Achievement> getAllAchievements() {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT a FROM Achievement a", Achievement.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Main method to test the achievement system
    public static void main(String[] args) {
        AchievementSystem achievementSystem = new AchievementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("
Available commands: add, list, exit");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "add":
                    System.out.print("Enter achievement name: ");
                    String achievementName = scanner.nextLine().trim();
                    achievementSystem.addAchievement(achievementName);
                    break;
                case "list":
                    List<Achievement> achievements = achievementSystem.getAllAchievements();
                    for (Achievement achievement : achievements) {
                        System.out.println("Achievement: " + achievement.getName());
                    }
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }
    }
}

/**
 * Achievement.java
 * Represents an achievement in the system.
 */
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Achievement() {}

    public Achievement(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
