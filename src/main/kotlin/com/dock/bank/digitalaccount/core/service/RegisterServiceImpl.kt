package com.dock.bank.digitalaccount.core.service

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import com.dock.bank.digitalaccount.core.domain.User
import com.dock.bank.digitalaccount.core.port.persistence.UserPersistence
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class RegisterServiceImpl(
    private val userPersistence: UserPersistence
): RegisterService {
    companion object {
        private val logger = LoggerFactory.getLogger(RegisterServiceImpl::class.java)
    }
    override fun register(user: User) : User {
        val enc = BCryptPasswordEncoder()
        val password = enc.encode(user.password)
        logger.info("Encrypting password from user with id ${user.id} and username: ${user.username}")
        return userPersistence.create(user.copy(password = password))
    }
}