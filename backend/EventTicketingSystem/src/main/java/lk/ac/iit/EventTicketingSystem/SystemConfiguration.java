package lk.ac.iit.EventTicketingSystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SystemConfiguration {
    private int totalTicketsInPool = 500;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketPoolCapacity = 600;

    public SystemConfiguration() {
    }

    public SystemConfiguration(int totalTicketsInPool, int ticketReleaseRate, int customerRetrievalRate, int maxTicketPoolCapacity) {
        this.totalTicketsInPool = totalTicketsInPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
        this.maxTicketPoolCapacity = maxTicketPoolCapacity;
    }

    // thread pool config
    @Bean(name="treadPool")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("userThread-");
        executor.initialize();
        return executor;
    }


    // getters and setters

    public int getTotalTicketsInPool() {
        return totalTicketsInPool;
    }

    public void setTotalTicketsInPool(int totalTicketsInPool) {
        this.totalTicketsInPool = totalTicketsInPool;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketPoolCapacity() {
        return maxTicketPoolCapacity;
    }

    public void setMaxTicketPoolCapacity(int maxTicketPoolCapacity) {
        this.maxTicketPoolCapacity = maxTicketPoolCapacity;
    }

    @Override
    public String toString() {
        return "SystemConfiguration{" +
                "totalTicketsInPool=" + totalTicketsInPool +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketPoolCapacity=" + maxTicketPoolCapacity +
                '}';
    }
}
