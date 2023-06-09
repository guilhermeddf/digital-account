package com.dock.bank.digitalaccount.utils

import com.dock.bank.digitalaccount.infra.postgres.model.UserTable
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails(private val user: UserTable) : UserDetails {

    fun getId() = user.id
    override fun getAuthorities() = setOf(SimpleGrantedAuthority(user.role.toString()));
    override fun getPassword() = user.password

    override fun getUsername() = user.username

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}