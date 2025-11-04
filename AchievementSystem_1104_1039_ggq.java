// 代码生成时间: 2025-11-04 10:39:05
 * It uses Hibernate to interact with the database.
 */
package com.example.achievement;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "achievements")
public class Achievement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int points;

    // Default constructor
    public Achievement() {
    }

    // Constructor with parameters
    public Achievement(String title, String description, int points) {
        this.title = title;
        this.description = description;
        this.points = points;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}

package com.example.achievement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class AchievementService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Achievement createAchievement(Achievement achievement) {
        try {
            return entityManager.merge(achievement);
        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Optional<Achievement> getAchievement(Long id) {
        try {
            return Optional.ofNullable(entityManager.find(Achievement.class, id));
        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Transactional
    public List<Achievement> getAllAchievements() {
        try {
            return entityManager.createQuery("SELECT a FROM Achievement a", Achievement.class).getResultList();
        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public void updateAchievement(Achievement achievement) {
        try {
            entityManager.merge(achievement);
        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteAchievement(Long id) {
        try {
            Achievement achievement = entityManager.find(Achievement.class, id);
            if (achievement != null) {
                entityManager.remove(achievement);
            }
        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
        }
    }
}
