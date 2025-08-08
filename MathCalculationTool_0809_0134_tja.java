// 代码生成时间: 2025-08-09 01:34:41
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Properties;

public class MathCalculationTool {

    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;
    private static MathCalculationTool instance;

    // Private constructor to prevent instantiation
    private MathCalculationTool() {}

    // Returns the singleton instance of MathCalculationTool
    public static MathCalculationTool getInstance() {
        if (instance == null) {
            instance = new MathCalculationTool();
        }
        return instance;
    }

    // Initialize the Hibernate session factory
    public void initializeSessionFactory() {
        // Create the SessionFactory from hibernate.cfg.xml
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + "Exception: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Close the Hibernate session factory
    public void closeSessionFactory() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    // Start a new transaction
    public void beginTransaction() {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    // Commit the current transaction
    public void commitTransaction() {
        if (transaction != null) {
            transaction.commit();
        }
    }

    // Rollback the current transaction
    public void rollbackTransaction() {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    // Close the current session
    public void closeSession() {
        if (session != null) {
            session.close();
        }
    }

    // Add a new mathematical calculation to the database
    public void addCalculation(int operationType, double operand1, double operand2, double result) {
        try {
            beginTransaction();
            Calculation calculation = new Calculation(operationType, operand1, operand2, result);
            session.save(calculation);
            commitTransaction();
        } catch (Exception e) {
            rollbackTransaction();
            e.printStackTrace();
        } finally {
            closeSession();
        }
    }

    // Perform a mathematical operation and store the result
    public double performOperation(int operationType, double operand1, double operand2) {
        double result = 0;
        switch (operationType) {
            case 1: // Addition
                result = operand1 + operand2;
                break;
            case 2: // Subtraction
                result = operand1 - operand2;
                break;
            case 3: // Multiplication
                result = operand1 * operand2;
                break;
            case 4: // Division
                if (operand2 != 0) {
                    result = operand1 / operand2;
                } else {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid operation type.");
        }
        // Store the result in the database
        addCalculation(operationType, operand1, operand2, result);
        return result;
    }

    // Main method for testing
    public static void main(String[] args) {
        MathCalculationTool tool = MathCalculationTool.getInstance();
        tool.initializeSessionFactory();
        try {
            System.out.println("10 + 5 = " + tool.performOperation(1, 10, 5));
            System.out.println("10 - 5 = " + tool.performOperation(2, 10, 5));
            System.out.println("10 * 5 = " + tool.performOperation(3, 10, 5));
            System.out.println("10 / 5 = " + tool.performOperation(4, 10, 5));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            tool.closeSessionFactory();
        }
    }
}

/*
 * Calculation.java
 * Represents a mathematical calculation with an operation type, operands, and a result.
 */
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Calculation {
    @Id
    private int id;
    private int operationType;
    private double operand1;
    private double operand2;
    private double result;

    public Calculation() {}

    public Calculation(int operationType, double operand1, double operand2, double result) {
        this.operationType = operationType;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.result = result;
    }

    // Getters and setters for the fields
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOperationType() { return operationType; }
    public void setOperationType(int operationType) { this.operationType = operationType; }
    public double getOperand1() { return operand1; }
    public void setOperand1(double operand1) { this.operand1 = operand1; }
    public double getOperand2() { return operand2; }
    public void setOperand2(double operand2) { this.operand2 = operand2; }
    public double getResult() { return result; }
    public void setResult(double result) { this.result = result; }
}
