package com.example.backend.api

import com.example.backend.util.exceptions.BadRequestException
import com.example.backend.util.exceptions.ConflictException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.net.ConnectException
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class ApiExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequest(response: HttpServletResponse, ex: BadRequestException, request: WebRequest) {
        response.status = HttpStatus.BAD_REQUEST.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"error\": \"${ex.message}\"}")
        response.writer.flush()
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(response: HttpServletResponse, ex: BadCredentialsException, request: WebRequest) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"error\": \"${ex.message}\"}")
        response.writer.flush()
    }

    @ExceptionHandler(ConflictException::class)
    fun handleConflict(response: HttpServletResponse, ex: ConflictException, request: WebRequest) {
        response.status = HttpStatus.CONFLICT.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"error\": \"${ex.message}\"}")
        response.writer.flush()
    }

    @ExceptionHandler(ConnectException::class)
    fun handleConnect(response: HttpServletResponse, ex: ConflictException, request: WebRequest) {
        response.status = HttpStatus.INTERNAL_SERVER_ERROR.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write("{\"error\": \"${ex.message}\"}")
        response.writer.flush()
    }
}