// 代码生成时间: 2025-08-30 03:52:07
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.UUID;

// 定义消息实体类
class Message {
    private String id;
    private String title;
    private String content;

    // 构造函数、getter和setter省略...
}

// 定义消息通知系统类
public class MessageNotificationSystem {
    private static final Configuration configuration = new Configuration().configure();

    public static void main(String[] args) {
        // 初始化Hibernate会话
        Session session = configuration.buildSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();

            // 创建消息实例
            Message message = new Message();
            message.setId(UUID.randomUUID().toString());
            message.setTitle("This is a message title");
            message.setContent("This is the content of the message.");

            // 保存消息到数据库
            session.save(message);

            // 提交事务
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        // 查询所有消息
        try (Session session = configuration.buildSessionFactory().openSession()) {
            List<Message> messages = session.createQuery("from Message", Message.class).list();
            for (Message msg : messages) {
                System.out.println("Message ID: " + msg.getId() + ", Title: " + msg.getTitle() + ", Content: " + msg.getContent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}