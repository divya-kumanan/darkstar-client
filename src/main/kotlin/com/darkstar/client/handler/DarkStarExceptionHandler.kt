package com.darkstar.client.handler

import com.darkstar.client.exception.BadRequestException
import com.darkstar.client.exception.DarkStarServerException
import com.darkstar.client.exception.MissionNotFoundException
import com.darkstar.client.model.DarkStarResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class MarsShuttleExceptionHandler {
    @ExceptionHandler(DarkStarServerException::class)
    @ResponseStatus(HttpStatus.FAILED_DEPENDENCY)
    fun handleDarkStarServerException(ex: Exception): DarkStarResponse {
        return DarkStarResponse(message= ex.message, HttpStatus.FAILED_DEPENDENCY.value())
    }

    @ExceptionHandler(MissionNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleMissionNotFoundException(ex: Exception): DarkStarResponse {
        return DarkStarResponse(message= ex.message, HttpStatus.NOT_FOUND.value())
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(ex: Exception): DarkStarResponse {
        return DarkStarResponse(message= ex.message, HttpStatus.BAD_REQUEST.value())
    }
}