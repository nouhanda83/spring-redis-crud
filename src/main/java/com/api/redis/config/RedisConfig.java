package com.api.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Bean
  public LettuceConnectionFactory redisConnectionFactory(
      @Value("${spring.redis.host:localhost}") String host,
      @Value("${spring.redis.port:6379}") int port,
      @Value("${spring.redis.password:}") String password,
      @Value("${spring.redis.ssl.enabled:false}") boolean sslEnabled
  ) {
    RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration(host, port);
    if (!password.isEmpty()) {
      conf.setPassword(RedisPassword.of(password));
    }

    LettuceClientConfiguration.LettuceClientConfigurationBuilder b =
        LettuceClientConfiguration.builder();
    if (sslEnabled) {
      b.useSsl(); // senza argomenti nella versione in uso
    }

    return new LettuceConnectionFactory(conf, b.build());
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory cf) {
    RedisTemplate<String, Object> tpl = new RedisTemplate<>();
    tpl.setConnectionFactory(cf);

    StringRedisSerializer str = new StringRedisSerializer();
    GenericJackson2JsonRedisSerializer json = new GenericJackson2JsonRedisSerializer();

    // key/value
    tpl.setKeySerializer(str);
    tpl.setValueSerializer(json);

    // hash key/value (cruciale)
    tpl.setHashKeySerializer(str);
    tpl.setHashValueSerializer(json);

    tpl.afterPropertiesSet();
    return tpl;
  }
}
