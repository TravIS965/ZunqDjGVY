// 代码生成时间: 2025-10-25 17:41:26
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.List;
import java.util.ArrayList;
import java.lang.Math;

// 定义一个数据实体类，用于表示概率分布数据
class ProbabilityDistribution {
    private int id;
    private String name;
    private double mean;
    private double variance;

    // 构造方法、getter和setter省略
}

// 数据访问对象（DAO）类，用于访问概率分布数据
class ProbabilityDistributionDAO {
    private SessionFactory sessionFactory;

    public ProbabilityDistributionDAO() {
        // 从配置文件中获取SessionFactory
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<ProbabilityDistribution> getAllProbabilityDistributions() {
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            return session.createQuery("from ProbabilityDistribution").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}

// 服务类，用于计算概率分布
class ProbabilityDistributionService {
    private ProbabilityDistributionDAO probabilityDistributionDAO;

    public ProbabilityDistributionService() {
        probabilityDistributionDAO = new ProbabilityDistributionDAO();
    }

    // 计算概率分布的均值和方差
    public double[] calculateDistribution(double[] values) {
        double mean = 0;
        double variance = 0;
        for (double value : values) {
            mean += value;
        }
        mean /= values.length;
        for (double value : values) {
            variance += Math.pow(value - mean, 2);
        }
        variance /= values.length;
        return new double[]{mean, variance};
    }
}

// 主类，用于运行程序
public class ProbabilityDistributionCalculator {
    public static void main(String[] args) {
        ProbabilityDistributionService service = new ProbabilityDistributionService();
        double[] values = {1.0, 2.0, 3.0, 4.0, 5.0};
        double[] result = service.calculateDistribution(values);
        System.out.println("Mean: " + result[0] + ", Variance: " + result[1]);
    }
}