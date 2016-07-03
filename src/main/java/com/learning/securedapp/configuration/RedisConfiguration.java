package com.learning.securedapp.configuration;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.ExpiringSession;
import org.springframework.session.SessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.context.WebApplicationContext;

import com.learning.securedapp.web.domain.CachingCart;
import com.learning.securedapp.web.domain.Cart;
import com.learning.securedapp.web.repositories.InvalidClassExceptionSafeRepository;

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
        jedisConnectionFactory.setHostName("127.0.0.1");
        jedisConnectionFactory.setPort(6379);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setExposeConnection(true);
        redisTemplate.setKeySerializer(stringRedisSerializer());
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
    
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
       StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
       return stringRedisSerializer;
    }
    
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
     
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    Cart cart() {
        return new CachingCart();
    }
    
    @Primary
    @Bean
    public SessionRepository<? extends ExpiringSession> primarySessionRepository(RedisOperationsSessionRepository delegate) {
        return new InvalidClassExceptionSafeRepository(delegate);
    }

}
