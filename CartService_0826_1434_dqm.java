// 代码生成时间: 2025-08-26 14:34:08
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;

// 定义购物车实体类
class Cart {
    private int id;
    private List<Item> items;
    public Cart() {
        this.items = new ArrayList<Item>();
    }
    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
    public void addItem(Item item) { items.add(item); }
    public void removeItem(Item item) { items.remove(item); }
}

// 定义购物车中的商品实体类
class Item {
    private int id;
    private String name;
    private double price;
    // getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}

// 购物车服务类
public class CartService {
    private SessionFactory sessionFactory;
    public CartService() {
        // 初始化SessionFactory
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    // 获取购物车
    public Cart getCart(int cartId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Cart cart = (Cart) session.get(Cart.class, cartId);
            transaction.commit();
            return cart;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    // 添加商品到购物车
    public boolean addToCart(int cartId, Item item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Cart cart = (Cart) session.get(Cart.class, cartId);
            if (cart != null) {
                cart.addItem(item);
                session.update(cart);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    // 从购物车移除商品
    public boolean removeFromCart(int cartId, Item item) {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Cart cart = (Cart) session.get(Cart.class, cartId);
            if (cart != null) {
                cart.removeItem(item);
                session.update(cart);
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    // 关闭SessionFactory
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
