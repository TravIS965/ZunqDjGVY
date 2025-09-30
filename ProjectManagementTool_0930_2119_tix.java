// 代码生成时间: 2025-09-30 21:19:12
package com.example.projectmanagementtool;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.ArrayList;

// The Project class represents a project in the project management tool
class Project {
    private long id;
    private String name;
    private String description;

    // Constructors, getters and setters
    public Project() {
    }

    public Project(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
}

// The ProjectService class handles the business logic of the project management tool
class ProjectService {
    private SessionFactory sessionFactory;

    public ProjectService() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // Adds a new project to the database
    public void addProject(Project project) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(project);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    // Retrieves a list of all projects from the database
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        Session session = sessionFactory.openSession();
        try {
            Query<Project> query = session.createQuery("from Project", Project.class);
            projects = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return projects;
    }

    // Closes the session factory
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}

// The Main class is the entry point of the application
public class Main {
    public static void main(String[] args) {
        ProjectService projectService = new ProjectService();
        try {
            // Create a new project
            Project project = new Project();
            project.setName("Example Project");
            project.setDescription("This is an example project.");

            // Add the project to the database
            projectService.addProject(project);

            // Retrieve and print all projects
            List<Project> projects = projectService.getAllProjects();
            for (Project p : projects) {
                System.out.println("Project ID: " + p.getId() + ", Name: " + p.getName() + ", Description: " + p.getDescription());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            projectService.close();
        }
    }
}