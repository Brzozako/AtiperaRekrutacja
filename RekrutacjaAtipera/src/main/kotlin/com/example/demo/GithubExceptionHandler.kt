package com.example.demo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GithubExceptionHandler {
    @ExceptionHandler(GithubUserNotFoundException::class)
    fun handleNotFound(e: GithubUserNotFoundException): ResponseEntity<Map<String, Any>> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(mapOf("status" to 404, "message" to (e.message ?: "Unknown error")))

    }
}

