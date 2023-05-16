package com.dock.bank.digitalaccount.infra.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisConnection(private val redisConnection: RedisTemplate<String, String>) {

    fun save(key: String, value: String) {
        redisConnection.opsForValue().set(key, value)
        redisConnection.expire(key, 1, TimeUnit.DAYS)
    }

    fun get(key: String): String? {
        return redisConnection.opsForValue().get(key)
    }

    fun delete(key: String) {
        redisConnection.delete(key)
    }
}