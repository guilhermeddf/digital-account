package com.dock.bank.digitalaccount.infra.redis

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisPersistence(
    private val repository: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) {

    private val CACHE_PREFIX = "digital.account."
    private val DEFAULT_CACHE_DURATION = Duration.ofMinutes(60)

    companion object {
        private val logger = LoggerFactory.getLogger(RedisPersistence::class.java)
    }

    fun put(key: String, value: Any) {
        return put(key, value, DEFAULT_CACHE_DURATION)
    }

    fun put(key: String, value: Any, duration: Duration){
        try {
            repository.opsForValue().set(CACHE_PREFIX + key, objectMapper.writeValueAsString(value), duration)
        } catch (e: Exception) {
            logger.error("Redis failed to save key: $CACHE_PREFIX$key", e)
        }

    }

    fun get(key: String): Any? {
        return try {
            repository.opsForValue().get(CACHE_PREFIX + key)
        } catch (e: Exception) {
            logger.error("Error trying to get key: $CACHE_PREFIX$key", e)
            null
        }
    }

    fun delete(key: String) {
        try {
            repository.opsForValue().getAndDelete("$CACHE_PREFIX$key")
        } catch (e: Exception) {
            logger.error("Error trying to delete key: $CACHE_PREFIX$key")
        }
    }
}