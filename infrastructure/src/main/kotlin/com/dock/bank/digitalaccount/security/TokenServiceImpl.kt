/*package com.dock.bank.digitalaccount.security
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.dock.bank.digitalaccount.core.domain.Role
import com.dock.bank.digitalaccount.core.domain.User
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.stream.Collectors


@Service
class TokenServiceImpl {
    companion object {
        private val logger = LoggerFactory.getLogger(TokenServiceImpl::class.java)
    }

    fun tokenGenerator(user: User): String {
        logger.info("Generating access token to user with id: ${user.id} and username ${user.username}.")

        val authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(Role.ADMIN.name)

        return JWT.create()
            .withIssuer("test")
            .withSubject(user.username)
            .withClaim("id", user.id.toString())
            .withClaim("authorities", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .withExpiresAt(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00")))
            .sign(Algorithm.HMAC256("secret"))

    }

    fun getSubject(token: String): String {
        logger.info("Getting subject from JWT token.")
        val response = JWT.require(Algorithm.HMAC256("secret"))
            .withIssuer("test")
            .build().verify(token).subject
        return response
    }
}

 */