// 代码生成时间: 2025-08-24 03:19:19
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Properties;
import java.util.Scanner;

// 订单实体类
class Order {
    private int id;
    private String customerName;
    private double amount;

    // 构造方法、getter和setter省略...
}

// 订单处理服务类
class OrderProcessingService {
    private SessionFactory sessionFactory;

    public OrderProcessingService() {
        // Hibernate配置文件路径
        String hibernateConfigurationFile = 