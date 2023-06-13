package com.dock.bank.digitalaccount.core.usecase

import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.infra.postgres.HolderPersistenceImpl
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresUserRepository
import com.dock.bank.digitalaccount.utils.CustomUserDetails
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class FilterToken(
    private val tokeService: TokenServiceImpl,
    private val userRepository: PostgresUserRepository,
): OncePerRequestFilter() {
    companion object {
        private val logger = LoggerFactory.getLogger(FilterToken::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        if(!authorizationHeader.isNullOrBlank()) {
            logger.info("Getting user by token.")
            val token = authorizationHeader.replace("Bearer ", "")
            val subject = tokeService.getSubject(token)
            val user = CustomUserDetails(userRepository.findByUsername(subject).orElseThrow { ResourceNotFoundException("Username not found.") })
            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}