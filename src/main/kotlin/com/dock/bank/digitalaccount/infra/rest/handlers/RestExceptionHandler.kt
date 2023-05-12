package com.dock.bank.digitalaccount.infra.rest.handlers

import com.dock.bank.digitalaccount.core.exceptions.DomainException
import com.dock.bank.digitalaccount.core.exceptions.ErrorMessage
import com.dock.bank.digitalaccount.core.exceptions.LimitExceededException
import com.dock.bank.digitalaccount.core.exceptions.ResourceAlreadyExistsException
import com.dock.bank.digitalaccount.core.exceptions.ResourceNotFoundException
import com.dock.bank.digitalaccount.infra.RestClientException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.OffsetDateTime

@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(value = [RestClientException::class])
    fun handlerRestClientException(e: RestClientException) : ResponseEntity<ErrorMessage> {
        val error = ErrorMessage(
            timestamp = OffsetDateTime.now(),
            message = e.message,
            error = e.javaClass.name,
            status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        )
        return ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR)
    }

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