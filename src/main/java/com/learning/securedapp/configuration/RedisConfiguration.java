package com.learning.securedapp.configuration;

import com.learning.securedapp.web.domain.CachingCart;
import com.learning.securedapp.web.domain.Cart;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.context.WebApplicationContext;

/**
 * RedisConfiguration class.
 *
 * @author rajakolli
 *     <p>The @EnableRedisHttpSession annotation creates a Spring Bean with the name of
 *     springSessionRepositoryFilter that implements Filter. The filter is what is in charge of
 *     replacing the HttpSession implementation to be backed by Spring Session. In this instance
 *     Spring Session is backed by Redis.
 * @version $Id: $Id
 */
@Configuration(proxyBeanMethods = false)
@EnableCaching
@EnableRedisHttpSession
public class RedisConfiguration extends CachingConfigurerSupport {

    /**
     * jedisConnectionFactory.
     *
     * @return a {@link org.springframework.data.redis.connection.jedis.JedisConnectionFactory}
     *     object.
     */
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        return new JedisConnectionFactory(configuration);
    }

    /**
     * redisTemplate.
     *
     * @return a {@link org.springframework.data.redis.core.RedisTemplate} object.
     */
    @Bean
    public RedisTemplate<?, ?> redisTemplate() {
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setExposeConnection(true);
        redisTemplate.setKeySerializer(stringRedisSerializer());
        return redisTemplate;
    }

    /**
     * stringRedisSerializer.
     *
     * @return a {@link org.springframework.data.redis.serializer.StringRedisSerializer} object.
     */
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /** {@inheritDoc} */
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
}
