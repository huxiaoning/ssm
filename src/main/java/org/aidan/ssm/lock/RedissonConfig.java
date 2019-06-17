package org.aidan.ssm.lock;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aidan
 * @创建时间：2019/6/16 3:49 PM
 * @描述: RedissonConfig
 */
@Configuration
public class RedissonConfig {

    @Bean
    public Redisson redisson() {
        Config config = new Config();
        // use "rediss://" for SSL connection
        // config.useClusterServers().addNodeAddress("redis://192.168.100.100:6379");
        config.useSingleServer().setAddress("redis://localhost:6379");
        Redisson redisson = (Redisson) Redisson.create(config);
        return redisson;
    }

    @Bean
    public DistributedLock distributedLock() {
        Redisson redisson = redisson();
        DistributedLock distributedLock = new DistributedLock(redisson.getLock("redis-limit"));
        return distributedLock;
    }
}
