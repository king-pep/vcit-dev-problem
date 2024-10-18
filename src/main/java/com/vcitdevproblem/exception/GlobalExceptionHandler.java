package com.vcitdevproblem.exception;


import com.vcitdevproblem.dto.ClientResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * GlobalExceptionHandler is a centralized exception handling component that handles various exceptions
 * thrown by the application and returns appropriate responses with HTTP status codes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ClientNotFoundException and returns a response with HTTP 404 status.
     *
     * @param ex the exception thrown when a client is not found
     * @return a ResponseEntity containing a ClientResponse with the error message and 404 status code
     */
    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ClientResponse<String>> handleClientNotFound(ClientNotFoundException ex) {
        ClientResponse<String> response = new ClientResponse<>(
                404, "api-fm-404", ex.getMessage(), "Client not found.", null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles DuplicateIdException and returns a response with HTTP 400 status.
     *
     * @param ex the exception thrown when a duplicate ID number is encountered
     * @return a ResponseEntity containing a ClientResponse with the error message and 400 status code
     */
    @ExceptionHandler(DuplicateIdException.class)
    public ResponseEntity<ClientResponse<String>> handleDuplicateId(DuplicateIdException ex) {
        ClientResponse<String> response = new ClientResponse<>(
                400, "api-fm-400", ex.getMessage(), "Duplicate ID number.", null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles DuplicateMobileNumberException and returns a response with HTTP 400 status.
     *
     * @param ex the exception thrown when a duplicate mobile number is encountered
     * @return a ResponseEntity containing a ClientResponse with the error message and 400 status code
     */
    @ExceptionHandler(DuplicateMobileNumberException.class)
    public ResponseEntity<ClientResponse<String>> handleDuplicateMobileNumber(DuplicateMobileNumberException ex) {
        ClientResponse<String> response = new ClientResponse<>(
                400, "api-fm-400", ex.getMessage(), "Duplicate mobile number.", null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles InvalidIdNumberException and returns a response with HTTP 400 status.
     *
     * @param ex the exception thrown when an invalid ID number is encountered
     * @return a ResponseEntity containing a ClientResponse with the error message and 400 status code
     */
    @ExceptionHandler(InvalidIdNumberException.class)
    public ResponseEntity<ClientResponse<String>> handleInvalidIdNumber(InvalidIdNumberException ex) {
        ClientResponse<String> response = new ClientResponse<>(
                400, "api-fm-400", ex.getMessage(), "Invalid ID number.", null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException and returns a response with HTTP 400 status.
     * This is typically thrown when validation fails for a request object.
     *
     * @param ex the exception thrown when a method argument fails validation
     * @return a ResponseEntity containing a ClientResponse with the validation error message and 400 status code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ClientResponse<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ClientResponse<String> response = new ClientResponse<>(
                400, "api-fm-400", errorMessage, "Invalid input.", null);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles any general exceptions that are not explicitly handled by other methods
     * and returns a response with HTTP 500 status.
     *
     * @param ex the general exception thrown
     * @return a ResponseEntity containing a ClientResponse with the error message and 500 status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ClientResponse<String>> handleGeneralExceptions(Exception ex) {
        ClientResponse<String> response = new ClientResponse<>(
                500, "api-fm-500", "An unexpected error occurred.", ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
