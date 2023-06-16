package com.dock.bank.digitalaccount.security

import com.dock.bank.digitalaccount.core.exception.ResourceNotFoundException
import com.dock.bank.digitalaccount.infra.postgres.repository.PostgresUserRepository
import com.dock.bank.digitalaccount.postgresql.model.CustomUserDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val userRepository: PostgresUserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        return CustomUserDetails(userRepository.findByUsername(username).orElseThrow {
            ResourceNotFoundException("Username not found.")
        })
    }
}