package com.learning.securedapp.configuration;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 
 * @author rajakolli 
 * 
 * The @EnableRedisHttpSession annotation creates a Spring Bean with the
 * name of springSessionRepositoryFilter that implements Filter. The filter is what is in
 * charge of replacing the HttpSession implementation to be backed by Spring Session. In
 * this instance Spring Session is backed by Redis.
 */

@Configuration
@EnableRedisHttpSession
public class RedisConfiguration extends CachingConfigurerSupport {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setExposeConnection(true);
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisTemplate());
        redisCacheManager.setTransactionAware(true);
        redisCacheManager.setLoadRemoteCachesOnStartup(true);
        redisCacheManager.setUsePrefix(true);
        return redisCacheManager;
    }

}
