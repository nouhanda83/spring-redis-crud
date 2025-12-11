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

    LettuceClientConfiguration.LettuceClientConfigurationBuilder clientBuilder =
        LettuceClientConfiguration.builder();
    if (sslEnabled) {
      clientBuilder.useSsl();   // senza argomenti
    }
    LettuceClientConfiguration clientCfg = clientBuilder.build();

    return new LettuceConnectionFactory(conf, clientCfg);
  }

  @Bean
public RedisTemplate<String, com.api.redis.models.User> userRedisTemplate(LettuceConnectionFactory cf) {
  RedisTemplate<String, com.api.redis.models.User> tpl = new RedisTemplate<>();
  tpl.setConnectionFactory(cf);

  StringRedisSerializer stringSer = new StringRedisSerializer();
  Jackson2JsonRedisSerializer<com.api.redis.models.User> userJsonSer =
      new Jackson2JsonRedisSerializer<>(com.api.redis.models.User.class);

  tpl.setKeySerializer(stringSer);
  tpl.setValueSerializer(userJsonSer);
  tpl.setHashKeySerializer(stringSer);
  tpl.setHashValueSerializer(userJsonSer);
  tpl.afterPropertiesSet();
  return tpl;
}
}

