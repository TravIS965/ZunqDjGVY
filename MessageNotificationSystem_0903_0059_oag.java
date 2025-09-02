// 代码生成时间: 2025-09-03 00:59:09
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import java.util.Properties;

// 定义消息实体类
class Message {
    private int id;
    private String title;
    private String content;

    // Getter和Setter方法
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}

// 定义消息通知系统
public class MessageNotificationSystem {

    private static SessionFactory sessionFactory;

    // 初始化Hibernate SessionFactory
    static {
        try {
            // 创建SessionFactory对象
            BootstrapServiceRegistryBuilder builder = new BootstrapServiceRegistryBuilder();
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + "
" + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // 获取Session对象
    public static Session getSession() throws Exception {
        return sessionFactory.openSession();
    }

    // 发送消息
    public void sendMessage(String title, String content) {
        try (Session session = getSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                Message message = new Message();
                message.setTitle(title);
                message.setContent(content);
                session.save(message);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 获取所有消息
    public Message[] getAllMessages() {
        try (Session session = getSession()) {
            return session.createQuery("from Message", Message.class)
                    .getResultList().toArray(new Message[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message[0];
        }
    }
}
