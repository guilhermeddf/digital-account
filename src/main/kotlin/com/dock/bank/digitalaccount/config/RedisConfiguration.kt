/*package com.dock.bank.digitalaccount.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
@EnableCaching
class RedisConfiguration(
    @Value("\${spring.cache.host}") private val host: String,
    @Value("\${spring.cache.port}") private val port: Int
) {

    @Bean
    fun redis(): JedisConnectionFactory {
        val configuration = RedisStandaloneConfiguration()
        configuration.hostName = host
        configuration.port = port
        return JedisConnectionFactory(configuration)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redis()
        return template
    }
} */
