package com.dock.bank.digitalaccount.core.service

import com.dock.bank.digitalaccount.core.domain.Holder
import com.dock.bank.digitalaccount.core.exception.DomainException
import com.dock.bank.digitalaccount.core.exception.ResourceAlreadyExistsException
import com.dock.bank.digitalaccount.core.utils.buildHolder
import com.dock.bank.digitalaccount.ports.spi.database.HolderDatabasePort
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.util.*

@ExtendWith(MockKExtension::class)
@DisplayName(value = "holder use case tests")
class HolderServicePortImplTest {

    @MockK
    private lateinit var holderDatabasePort: HolderDatabasePort

    @InjectMockKs
    private lateinit var holderServicePort: HolderServicePortImpl

    private val holderSlot = slot<Holder>()

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    @DisplayName(value = "should save a holder with success")
    fun `should save a holder with success`() {
        runBlocking {
            val fakeHolder = buildHolder(cpf = "72052325387")

            coEvery { holderDatabasePort.create(any()) } returns Optional.of(fakeHolder)

            holderServicePort.create(fakeHolder)

            coVerify(exactly = 1) { holderDatabasePort.create(capture(holderSlot)) }

            assertEquals(fakeHolder.id, holderSlot.captured.id)
            assertEquals(fakeHolder.cpf, holderSlot.captured.cpf)
            assertEquals(fakeHolder.name, holderSlot.captured.name)
        }
    }

    @Test
    @DisplayName(value = "should throw an exception if holder already exists")
    fun `should throw an exception if holder already exists`() {
        runBlocking {
            val fakeHolder = buildHolder()

            coEvery { holderDatabasePort.create(any()) } returns Optional.empty()

            val error = assertThrows<ResourceAlreadyExistsException> {
                holderServicePort.create(fakeHolder)
            }
            assertEquals("Holder already exists.", error.message)
        }
    }

    @Test
    @DisplayName(value = "should throw an exception if holder cpf is invalid")
    fun `should throw an exception if holder cpf is invalid`() {
        runBlocking {
            val fakeHolder = buildHolder(cpf = "00000000000")

            coEvery { holderDatabasePort.create(any()) } returns Optional.empty()

            val error = assertThrows<DomainException> {
                holderServicePort.create(fakeHolder)
            }
            assertEquals("Holder cpf is not valid.", error.message)
        }
    }

    @Test
    @DisplayName(value = "should delete a holder with success")
    fun `should delete a holder with success`() {
        runBlocking {
            val holderId = UUID.randomUUID()

            coEvery { holderDatabasePort.delete(holderId) } returns Unit

            holderServicePort.delete(holderId)

            coVerify(exactly = 1) { holderDatabasePort.delete(holderId) }
        }
    }
}