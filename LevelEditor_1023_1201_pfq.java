// 代码生成时间: 2025-10-23 12:01:22
package com.example.leveleditor;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

// Represents a level in the game
class Level {
    private int id;
    private String name;
    private String description;

    // Constructor, getters and setters
    public Level() {}
    public Level(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}

// The LevelEditor class provides a simple command line interface for editing levels
public class LevelEditor {
    private static final Scanner scanner = new Scanner(System.in);
    private Session session;

    public LevelEditor() {
        // Initialize Hibernate session
        Configuration configuration = new Configuration().configure();
        session = configuration.buildSessionFactory().openSession();
    }

    public void start() {
        try {
            while (true) {
                System.out.println("Enter command (add, list, update, delete, quit): ");
                String command = scanner.nextLine();

                switch (command) {
                    case "add":
                        addLevel();
                        break;
                    case "list":
                        listLevels();
                        break;
                    case "update":
                        updateLevel();
                        break;
                    case "delete":
                        deleteLevel();
                        break;
                    case "quit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid command.");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private void addLevel() {
        System.out.println("Enter level name: ");
        String name = scanner.nextLine();
        System.out.println("Enter level description: ");
        String description = scanner.nextLine();

        Transaction transaction = session.beginTransaction();
        try {
            Level level = new Level(0, name, description);
            session.save(level);
            transaction.commit();
            System.out.println("Level added successfully.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    private void listLevels() {
        Transaction transaction = session.beginTransaction();
        try {
            Query<Level> query = session.createQuery("FROM Level", Level.class);
            List<Level> levels = query.list();
            for (Level level : levels) {
                System.out.println("ID: " + level.getId() + ", Name: " + level.getName() + ", Description: " + level.getDescription());
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    private void updateLevel() {
        System.out.println("Enter level ID to update: ");
        int id = Integer.parseInt(scanner.nextLine());

        Transaction transaction = session.beginTransaction();
        try {
            Level level = session.get(Level.class, id);
            if (level != null) {
                System.out.println("Enter new name: ");
                String name = scanner.nextLine();
                System.out.println("Enter new description: ");
                String description = scanner.nextLine();

                level.setName(name);
                level.setDescription(description);

                session.update(level);
                transaction.commit();
                System.out.println("Level updated successfully.");
            } else {
                System.out.println("Level not found.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    private void deleteLevel() {
        System.out.println("Enter level ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        Transaction transaction = session.beginTransaction();
        try {
            Level level = session.get(Level.class, id);
            if (level != null) {
                session.delete(level);
                transaction.commit();
                System.out.println("Level deleted successfully.");
            } else {
                System.out.println("Level not found.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LevelEditor().start();
    }
}
