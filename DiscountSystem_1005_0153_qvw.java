// 代码生成时间: 2025-10-05 01:53:26
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.Properties;

// 折扣优惠实体类
class Discount {
    private int id;
    private String name;
    private double discount;

    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getDiscount() { return discount; }
    public void setDiscount(double discount) { this.discount = discount; }
    // ...
}

// 折扣优惠系统服务类
class DiscountService {
    private SessionFactory sessionFactory;

    public DiscountService() {
        // 加载Hibernate配置文件
        Configuration configuration = new Configuration().configure();
        // 设置数据库连接属性
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/your_database");
        properties.setProperty("hibernate.connection.username", "your_username");
        properties.setProperty("hibernate.connection.password", "your_password");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.setProperties(properties);
        // 创建SessionFactory实例
        this.sessionFactory = configuration.buildSessionFactory();
    }

    // 添加折扣优惠
    public void addDiscount(Discount discount) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(discount);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除折扣优惠
    public void removeDiscount(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Discount discount = session.get(Discount.class, id);
            if (discount != null) {
                session.delete(discount);
                transaction.commit();
            } else {
                transaction.rollback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 更新折扣优惠
    public void updateDiscount(Discount discount) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(discount);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取所有折扣优惠
    public List<Discount> getAllDiscounts() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Discount", Discount.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

// 折扣优惠系统主类
public class DiscountSystem {
    public static void main(String[] args) {
        // 创建折扣优惠服务实例
        DiscountService discountService = new DiscountService();

        // 示例：添加折扣优惠
        Discount discount = new Discount();
        discount.setName("Summer Sale");
        discount.setDiscount(0.1);
        discountService.addDiscount(discount);

        // 示例：获取所有折扣优惠
        List<Discount> discounts = discountService.getAllDiscounts();
        for (Discount d : discounts) {
            System.out.println("Discount Name: " + d.getName() + ", Discount: " + d.getDiscount());
        }
    }
}
