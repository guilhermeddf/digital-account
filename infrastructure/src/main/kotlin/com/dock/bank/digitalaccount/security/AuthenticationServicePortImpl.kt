/* package com.dock.bank.digitalaccount.security

import com.dock.bank.digitalaccount.core.domain.Login
import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.postgresql.repository.PostgresUserRepository
import com.dock.bank.digitalaccount.ports.spi.AuthenticationServicePort
import com.dock.bank.digitalaccount.postgresql.mapper.toEntity
import com.dock.bank.digitalaccount.postgresql.mapper.toTable
import com.dock.bank.digitalaccount.postgresql.model.CustomUserDetails
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationServicePortImpl(
    private val userRepository: PostgresUserRepository,
    private val manager: AuthenticationManager,
    private val tokenService: TokenServiceImpl
): UserDetailsService, AuthenticationServicePort {

    companion object {
        private val logger = LoggerFactory.getLogger(AuthenticationServicePortImpl::class.java)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return CustomUserDetails(userRepository.findByUsername(username).orElseThrow {
            ResourceNotFoundException("Username not found.")
        })
    }

    override fun authenticate(login: Login): Login {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(login.username, login.password)
        val authenticate  = manager.authenticate(usernamePasswordAuthenticationToken)
        val customUserDetails = authenticate.principal as CustomUserDetails
        val user = customUserDetails.toEntity()

        logger.info("User ${user.username} was logged with success with roles: ${user.role}")
        return login.copy(token = tokenService.tokenGenerator(user))
    }
}

 */