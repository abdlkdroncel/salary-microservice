package com.hrglobal.salary.exception.handler;

import com.hrglobal.salary.exception.enums.FriendlyMessageCodes;
import com.hrglobal.salary.exception.exceptions.SalaryAlreadyDeletedException;
import com.hrglobal.salary.exception.exceptions.SalaryNotCalculatedException;
import com.hrglobal.salary.exception.exceptions.SalaryNotFoundException;
import com.hrglobal.salary.exception.utils.FriendlyMessageUtils;
import com.hrglobal.salary.response.FriendlyMessage;
import com.hrglobal.salary.response.InternalApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SalaryNotCalculatedException.class)
    public InternalApiResponse<String> handleSalaryNotCalculatedException(SalaryNotCalculatedException exception) {
        return InternalApiResponse.<String >builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SalaryNotFoundException.class)
    public InternalApiResponse<String> handleSalaryNotFoundException(SalaryNotFoundException exception) {
        return InternalApiResponse.<String >builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.NOT_FOUND)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SalaryAlreadyDeletedException.class)
    public InternalApiResponse<String> handleSalaryAlreadyDeletedException(SalaryAlreadyDeletedException exception) {
        return InternalApiResponse.<String >builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), FriendlyMessageCodes.ERROR))
                        .description(FriendlyMessageUtils.getFriendlyMessage(exception.getLanguage(), exception.getFriendlyMessageCode()))
                        .build())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .hasError(true)
                .errorMessages(Collections.singletonList(exception.getMessage()))
                .build();
    }
}
