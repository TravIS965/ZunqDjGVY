// 代码生成时间: 2025-10-09 02:57:30
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;

// Define the Rectangle class
class Rectangle {
    private int id;
    private double x;
    private double y;
    private double width;
    private double height;

    public Rectangle() {}

    public Rectangle(int id, double x, double y, double width, double height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    // Getters and setters for the rectangle coordinates
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public double getX() { return x; }
    public void setX(double x) { this.x = x; }
    public double getY() { return y; }
    public void setY(double y) { this.y = y; }
    public double getWidth() { return width; }
    public void setWidth(double width) { this.width = width; }
    public double getHeight() { return height; }
    public void setHeight(double height) { this.height = height; }

    // Method to check if this rectangle intersects with another rectangle
    public boolean intersects(Rectangle other) {
        return !(this.x + this.width < other.x ||
                this.x > other.x + other.width ||
                this.y + this.height < other.y ||
                this.y > other.y + other.height);
    }
}

// Define the CollisionDetectionService class
class CollisionDetectionService {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + "
" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Method to check for collision between two rectangles
    public boolean checkCollision(Rectangle rect1, Rectangle rect2) {
        if (rect1 == null || rect2 == null) {
            throw new IllegalArgumentException("Rectangle objects cannot be null.");
        }

        return rect1.intersects(rect2);
    }

    // Method to find all rectangles that intersect with a given rectangle
    public List<Rectangle> findIntersectingRectangles(Rectangle rect) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Rectangle> rectangles = session.createQuery("from Rectangle", Rectangle.class).list();
            List<Rectangle> intersectingRectangles = new ArrayList<>();

            for (Rectangle r : rectangles) {
                if (rect.intersects(r) && !rect.equals(r)) {
                    intersectingRectangles.add(r);
                }
            }

            tx.commit();
            return intersectingRectangles;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Error in finding intersecting rectangles.", e);
        } finally {
            session.close();
        }
    }
}

// Define the main class to run the collision detection system
public class CollisionDetectionSystem {
    public static void main(String[] args) {
        CollisionDetectionService service = new CollisionDetectionService();

        try {
            // Create two sample rectangles for collision testing
            Rectangle rect1 = new Rectangle(1, 10, 10, 50, 50);
            Rectangle rect2 = new Rectangle(2, 60, 60, 50, 50);
            Rectangle rect3 = new Rectangle(3, 15, 15, 30, 30);

            // Check if rect1 and rect2 intersect
            boolean collision = service.checkCollision(rect1, rect2);
            System.out.println("Rect1 and Rect2 intersect: " + collision);

            // Find all rectangles that intersect with rect3
            List<Rectangle> intersectingRects = service.findIntersectingRectangles(rect3);
            for (Rectangle r : intersectingRects) {
                System.out.println("Intersecting rectangle: ID = " + r.getId() + ", X = " + r.getX() + ", Y = " + r.getY());
            }

        } catch (Exception e) {
            System.err.println("Error in collision detection system: " + e.getMessage());
            e.printStackTrace();
        }
    }
}