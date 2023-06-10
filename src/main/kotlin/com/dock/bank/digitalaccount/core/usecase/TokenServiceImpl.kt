/* package com.dock.bank.digitalaccount.core.usecase
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dock.bank.digitalaccount.infra.postgres.model.UserTable
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenServiceImpl {

    fun tokenGenerator(user: UserTable): String {
        return JWT.create()
            .withIssuer("test")
            .withSubject(user.username)
            .withClaim("id", user.id.toString())
            .withExpiresAt(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00")))
            .sign(Algorithm.HMAC256("secret"))

    }

    fun getSubject(token: String): String {
        return JWT.require(Algorithm.HMAC256("secret"))
            .withIssuer("test").build()
            .verify(token).subject
    }
}

 */