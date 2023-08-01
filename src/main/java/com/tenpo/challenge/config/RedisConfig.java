package com.tenpo.challenge.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.distributed.proxy.ClientSideConfig;
import io.github.bucket4j.redis.redisson.cas.RedissonBasedProxyManager;
import org.redisson.api.RedissonClient;
import org.redisson.command.CommandExecutor;
import org.redisson.command.CommandSyncService;
import org.redisson.config.Config;
import org.redisson.config.ConfigSupport;
import org.redisson.connection.ConnectionManager;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

@EnableCaching
@EnableWebMvc
@Configuration
public class RedisConfig {

    @Bean
    public RateLimiter rateLimiter() throws IOException {
        return new RateLimiter(proxyManager(), bucketConfiguration());
    }

    @Bean(destroyMethod = "shutdown")
    public ConnectionManager redissonConnectionManager() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("redis.yml");
        Config config = Config.fromYAML(input);
        return ConfigSupport.createConnectionManager(config);
    }

    @Bean
    public CommandExecutor commandExecutor() throws IOException {
        return new CommandSyncService(redissonConnectionManager());
    }

    @Bean
    public RedissonBasedProxyManager proxyManager() throws IOException {
        return new RedissonBasedProxyManager(commandExecutor(),
                ClientSideConfig.getDefault(),
                Duration.ofMinutes(10));
    }

    @Bean
    public BucketConfiguration bucketConfiguration() {
        return BucketConfiguration.builder()
                .addLimit(Bandwidth.simple(3, Duration.ofMinutes(1)))
                .build();
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) throws IOException {
        String configFileName = "cache-config.yml";
        return new RedissonSpringCacheManager(redissonClient, "classpath:" + configFileName);
    }
}


