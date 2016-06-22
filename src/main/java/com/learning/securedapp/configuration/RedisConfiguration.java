package com.learning.securedapp.configuration;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
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
@EnableCaching
@EnableRedisHttpSession
public class RedisConfiguration extends CachingConfigurerSupport {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName("localhost");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setExposeConnection(true);
//        redisTemplate.setKeySerializer(stringRedisSerializer());
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
    
   /* @Bean
    public StringRedisSerializer stringRedisSerializer() {
       StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
       return stringRedisSerializer;
    }*/
    
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
      return (target, method, params) -> {
        StringBuilder sb = new StringBuilder();
        sb.append(target.getClass().getName());
        sb.append(method.getName());
        for (Object obj : params) {
          sb.append(obj.toString());
        }
        return sb.toString();
      };
    }
     

}
