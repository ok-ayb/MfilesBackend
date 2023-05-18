package io.xhub.smwall.exceptions;

import io.xhub.smwall.constants.ApiClientErrorCodes;
import io.xhub.smwall.mappers.http.FieldErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final FieldErrorMapper fieldErrorMapper;

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBusinessException(BusinessException e) {
        return e.getErrorMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBusinessException(MethodArgumentNotValidException e) {
        return ApiClientErrorCodes
                .INVALID_COMMAND_ARGS
                .getErrorMessage(e
                        .getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(error -> fieldErrorMapper.toDTO((FieldError) error))
                        .toArray()
                );
    }

}