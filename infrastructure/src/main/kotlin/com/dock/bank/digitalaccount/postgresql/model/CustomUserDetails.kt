package com.dock.bank.digitalaccount.postgresql.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class CustomUserDetails(private val user: UserTable) : UserDetails {

    fun getId() = user.id
    override fun getAuthorities() = listOf(SimpleGrantedAuthority(user.role.name));
    override fun getPassword() = user.password
    override fun getUsername() = user.username
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}