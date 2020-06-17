package com.javieraviles.springredis;

import com.javieraviles.springredis.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.javieraviles.springredis.entities.User;

@SpringBootApplication
@EnableRedisRepositories
@Configuration
public class SpringRedisApplication implements ApplicationRunner {

	@Value(value = "${redis.hostname}")
	private String redisHostname;

	@Value(value = "${redis.port}")
	private int redisPort;

	@Autowired
	private UserService userService;

	public static void main(final String[] args) {
		SpringApplication.run(SpringRedisApplication.class, args);
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHostname, redisPort);
		return new JedisConnectionFactory(redisStandaloneConfiguration);
	}

	@Bean
	public RedisTemplate<String, User> userTemplate() {
		RedisTemplate<String, User> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		return template;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		User user1 = new User();
		user1.setEmail("test@test.com");
		user1.setName("Test1");
		userService.save(user1);
	}
}
