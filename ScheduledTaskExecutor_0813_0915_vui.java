// 代码生成时间: 2025-08-13 09:15:29
// 定时任务调度器
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.JobExecutionContext;
import org.quartz.Job;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// 实现Job接口，定义定时任务逻辑
public class ScheduledTask implements Job {
    public void execute(JobExecutionContext context) {
        System.out.println("定时任务执行：" + new Date());
        // 在这里添加任务逻辑
    }
}

public class ScheduledTaskExecutor {
    
    // 启动定时任务
    public static void startScheduledTask() {
        try {
            // 创建调度器
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            // 定义工作
            JobDetail job = JobBuilder.newJob(ScheduledTask.class)
                .withIdentity("scheduledTask", "group1")
                .build();

            // 定义触发器
            Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("scheduledTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(40)
                    .repeatForever())
                .build();

            // 注册工作和触发器
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 启动定时任务
        startScheduledTask();
    }
}
