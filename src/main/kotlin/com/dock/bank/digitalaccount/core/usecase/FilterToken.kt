package com.dock.bank.digitalaccount.core.usecase


import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresUserRepository
import com.dock.bank.digitalaccount.utils.CustomUserDetails
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class FilterToken(
    private val tokeService: TokenServiceImpl,
    private val userRepository: PostgresUserRepository,
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        if(!authorizationHeader.isNullOrBlank()) {
            val token = authorizationHeader.replace("Bearer ", "")
            val subject = tokeService.getSubject(token)
            val user = CustomUserDetails(userRepository.findByUsername(subject).orElseThrow { ResourceNotFoundException("Username not found.") })
            val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }
}