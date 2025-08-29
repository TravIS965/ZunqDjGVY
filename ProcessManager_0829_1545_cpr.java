// 代码生成时间: 2025-08-29 15:45:47
import org.hibernate.Session;
    import org.hibernate.SessionFactory;
    import org.hibernate.Transaction;
    import org.hibernate.cfg.Configuration;
    import java.util.List;
    import java.util.ArrayList;
    
    // Process class to represent a process entity
    class Process {
        private int id;
        private String name;
        private boolean running;
        
        // Constructor, getters and setters
        public Process(int id, String name, boolean running) {
            this.id = id;
            this.name = name;
            this.running = running;
        }
        
        public int getId() {
            return id;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public boolean isRunning() {
            return running;
        }
        
        public void setRunning(boolean running) {
            this.running = running;
        }
    }
    
    // ProcessManager class to manage processes
    public class ProcessManager {
        private SessionFactory sessionFactory;
        
        // Constructor to initialize the SessionFactory
        public ProcessManager() {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        
        // Method to start a process
        public boolean startProcess(int processId) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    
                    Process process = session.get(Process.class, processId);
                    if (process == null) {
                        throw new IllegalArgumentException("Process with id " + processId + " not found.");
                    }
                    
                    process.setRunning(true);
                    session.update(process);
                    transaction.commit();
                    return true;
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw new RuntimeException("Failed to start process with id " + processId, e);
                } finally {
                    session.close();
                }
            }
        }
        
        // Method to stop a process
        public boolean stopProcess(int processId) {
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    
                    Process process = session.get(Process.class, processId);
                    if (process == null) {
                        throw new IllegalArgumentException("Process with id " + processId + " not found.");
                    }
                    
                    process.setRunning(false);
                    session.update(process);
                    transaction.commit();
                    return true;
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    throw new RuntimeException("Failed to stop process with id " + processId, e);
                } finally {
                    session.close();
                }
            }
        }
        
        // Method to get a list of all processes
        public List<Process> getAllProcesses() {
            List<Process> processes = new ArrayList<>();
            try (Session session = sessionFactory.openSession()) {
                List<Object[]> results = session.createQuery("FROM Process").getResultList();
                for (Object[] result : results) {
                    Process process = new Process((Integer) result[0], (String) result[1], (Boolean) result[2]);
                    processes.add(process);
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to get all processes", e);
            }
            return processes;
        }
        
        // Main method for testing
        public static void main(String[] args) {
            ProcessManager manager = new ProcessManager();
            try {
                // Start a process with id 1
                boolean started = manager.startProcess(1);
                System.out.println("Process started: " + started);
                
                // Stop a process with id 1
                boolean stopped = manager.stopProcess(1);
                System.out.println("Process stopped: " + stopped);
                
                // Get all processes
                List<Process> processes = manager.getAllProcesses();
                for (Process process : processes) {
                    System.out.println("Process ID: " + process.getId() + ", Name: " + process.getName() + ", Running: " + process.isRunning());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }