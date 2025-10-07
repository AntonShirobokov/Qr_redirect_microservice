package com.shirobokov.qr_redirect_microservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RedirectExceptionHandler {

    @ExceptionHandler(QrRouteNotFound.class)
    public ResponseEntity<?> qrRouteNotFound() {
        String html = """
                    <html>
                        <head><title>Not Found</title></head>
                        <body style="font-family: sans-serif; text-align:center; margin-top: 50px;">
                            <h1>404 — Ссылка не найдена</h1>
                            <p>К сожалению, по данному идентификатору запись в базе данных отсутствует.</p>
                        </body>
                    </html>
                    """;
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.parseMediaType("text/html; charset=UTF-8"))
                .body(html);
    }
}
