package com.dock.bank.digitalaccount.core.usecase
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dock.bank.digitalaccount.infra.postgres.model.UserTable
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenServiceImpl {
    companion object {
        private val logger = LoggerFactory.getLogger(TokenServiceImpl::class.java)
    }

    fun tokenGenerator(user: UserTable): String {
        logger.info("Generating access token to user with id: ${user.id} and username ${user.username}.")
        return JWT.create()
            .withIssuer("test")
            .withSubject(user.username)
            .withClaim("id", user.id.toString())
            .withExpiresAt(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00")))
            .sign(Algorithm.HMAC256("secret"))
    }

    fun getSubject(token: String): String {
        logger.info("Getting subject from JWT token.")
        return JWT.require(Algorithm.HMAC256("secret"))
            .withIssuer("test").build()
            .verify(token).subject
    }
}