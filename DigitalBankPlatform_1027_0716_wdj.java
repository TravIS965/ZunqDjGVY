// 代码生成时间: 2025-10-27 07:16:39
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// 数字银行平台实体类
class Account {
    private Long id;
    private String owner;
    private BigDecimal balance;

    // 省略构造函数、getter和setter方法
}

// 数字银行平台服务类
class DigitalBankService {
    private EntityManagerFactory emf;

    public DigitalBankService() {
        emf = Persistence.createEntityManagerFactory("DigitalBankPlatformPU");
    }

    // 创建账户
    public Account createAccount(String owner, BigDecimal initialBalance) {
        EntityManager em = emf.createEntityManager();
        try {
            Account account = new Account();
            account.setOwner(owner);
            account.setBalance(initialBalance);
            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
            return account;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to create account", e);
        } finally {
            em.close();
        }
    }

    // 获取账户
    public Optional<Account> getAccount(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Account.class, id));
        } finally {
            em.close();
        }
    }

    // 转账
    public boolean transfer(Long fromId, Long toId, BigDecimal amount) {
        EntityManager em = emf.createEntityManager();
        try {
            Account fromAccount = em.find(Account.class, fromId);
            Account toAccount = em.find(Account.class, toId);
            if (fromAccount == null || toAccount == null) {
                throw new IllegalArgumentException("Accounts not found");
            }
            if (fromAccount.getBalance().compareTo(amount) < 0) {
                throw new IllegalArgumentException("Insufficient balance");
            }
            em.getTransaction().begin();
            fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
            toAccount.setBalance(toAccount.getBalance().add(amount));
            em.persist(fromAccount);
            em.persist(toAccount);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Failed to transfer funds", e);
        } finally {
            em.close();
        }
    }

    // 关闭EntityManagerFactory
    public void close() {
        emf.close();
    }
}

// 主类
public class DigitalBankPlatform {
    public static void main(String[] args) {
        DigitalBankService service = new DigitalBankService();
        try {
            // 创建账户
            Account account1 = service.createAccount("John Doe", new BigDecimal("10000"));
            Account account2 = service.createAccount("Jane Doe", new BigDecimal("5000"));

            // 转账
            boolean success = service.transfer(account1.getId(), account2.getId(), new BigDecimal("2000"));
            if (success) {
                System.out.println("Transfer successful");
            } else {
                System.out.println("Transfer failed");
            }

            // 获取账户
            Optional<Account> accountOptional = service.getAccount(account1.getId());
            accountOptional.ifPresent(account -> {
                System.out.println("Account Owner: " + account.getOwner());
                System.out.println("Account Balance: " + account.getBalance());
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.close();
        }
    }
}
