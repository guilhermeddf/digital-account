package com.dock.bank.digitalaccount.core.domain

import com.dock.bank.digitalaccount.core.exceptions.DomainException
import com.dock.bank.digitalaccount.utils.buildAccount
import io.mockk.MockKAnnotations
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@DisplayName("account domain tests")
internal class AccountTest {

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    @DisplayName("should throw an exception when account is blocked")
    fun `should throw an exception when account is blocked` () {
        val fakeAccount = buildAccount(status = Status.BLOCKED)

        val error = assertThrows<DomainException> {
            fakeAccount.validateAccountIsBlocked()
        }

        assertEquals("Account is blocked.", error.message)
    }

    @Test
    @DisplayName("should throw an exception when account is disabled")
    fun `should throw an exception when account is disabled` () {
        val fakeAccount = buildAccount(status = Status.DISABLED)

        val error = assertThrows<DomainException> {
            fakeAccount.validateAccountIsDisabled()
        }

        assertEquals("Account is disabled.", error.message)
    }
}