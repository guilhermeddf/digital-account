package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.User
import com.dock.bank.digitalaccount.core.port.persistence.UserPersistence
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(
    private val userPersistence: UserPersistence
): RegisterService {

    override fun register(user: User) : User {
        val enc = BCryptPasswordEncoder()
        val password = enc.encode(user.password)
        return userPersistence.create(user.copy(password = password))
    }
}