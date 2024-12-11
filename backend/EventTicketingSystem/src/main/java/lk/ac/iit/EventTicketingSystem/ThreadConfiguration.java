package lk.ac.iit.EventTicketingSystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadConfiguration {

    // thread pool config (Vendor)
    @Bean(name="VendorThreadPool")
    public Executor VendorThread() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Vendor Thread");
        executor.initialize();
        return executor;
    }

    // thread pool config (Customer)
    @Bean(name="CustomerThreadPool")
    public Executor CustomerThread() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("Customer Thread");
        executor.initialize();
        return executor;
    }
}
