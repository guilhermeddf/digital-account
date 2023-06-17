package com.dock.bank.digitalaccount.api.exception

import com.dock.bank.digitalaccount.core.exception.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.OffsetDateTime

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(value = [ResourceNotFoundException::class])
    fun handlerResourceNotFountException(e: ResourceNotFoundException) : ResponseEntity<ErrorMessage> {
        val error = ErrorMessage(
            timestamp = OffsetDateTime.now(),
            message = e.message,
            error = e.javaClass.name,
            status = HttpStatus.NOT_FOUND.value()
        )
        return ResponseEntity(error, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [ResourceAlreadyExistsException::class])
    fun handlerResourceAlreadyExistsException(e: ResourceAlreadyExistsException) : ResponseEntity<ErrorMessage> {
        val error = ErrorMessage(
            timestamp = OffsetDateTime.now(),
            message = e.message,
            error = e.javaClass.name,
            status = HttpStatus.CONFLICT.value()
        )
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [DomainException::class])
    fun handlerDomainException(e: DomainException) : ResponseEntity<ErrorMessage> {
        val error = ErrorMessage(
            timestamp = OffsetDateTime.now(),
            message = e.message,
            error = e.javaClass.name,
            status = HttpStatus.CONFLICT.value()
        )
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(value = [LimitExceededException::class])
    fun handlerLimitExceededException(e: LimitExceededException) : ResponseEntity<ErrorMessage> {
        val error = ErrorMessage(
            timestamp = OffsetDateTime.now(),
            message = e.message,
            error = e.javaClass.name,
            status = HttpStatus.CONFLICT.value()
        )
        return ResponseEntity(error, HttpStatus.CONFLICT)
    }
}