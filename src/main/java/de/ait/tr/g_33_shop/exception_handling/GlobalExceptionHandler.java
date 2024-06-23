package de.ait.tr.g_33_shop.exception_handling;

import de.ait.tr.g_33_shop.exception_handling.exceptions.FourthTestException;
import de.ait.tr.g_33_shop.exception_handling.exceptions.NotActiveProductsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NotActiveProductsException.class)
    public ResponseEntity<Response> handleException(NotActiveProductsException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(FourthTestException.class)
    public ResponseEntity<Response> handleException(FourthTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
