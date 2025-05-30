package br.com.mpsolucoes.manager.exception.handler;


import br.com.mpsolucoes.manager.configuration.constants.MensagemConstants;
import br.com.mpsolucoes.manager.domain.dto.response.RestErrorResponse;
import br.com.mpsolucoes.manager.exception.BusinessException;
import br.com.mpsolucoes.manager.exception.RecordNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;


@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    ResponseEntity<RestErrorResponse> handleInternalError(final WebRequest request, final Exception exception) {
        log.error(exception.getMessage(), exception);

        final List<RestErrorResponse.FieldError> fieldErrors = Collections.emptyList();

        return createErrorResponse(request, HttpStatus.INTERNAL_SERVER_ERROR, exception, exception.getMessage(), fieldErrors);
    }

    @ExceptionHandler(RecordNotFoundException.class)
    ResponseEntity<RestErrorResponse> handleNotFound(final WebRequest request, final RecordNotFoundException exception) {
        final List<RestErrorResponse.FieldError> fieldErrors = Collections.emptyList();
        return createErrorResponse(request, HttpStatus.NOT_FOUND, exception, exception.getMessage(), fieldErrors);
    }

    @ExceptionHandler({ BusinessException.class })
    ResponseEntity<RestErrorResponse> handleBusiness(final WebRequest request, final BusinessException exception) {
        final List<RestErrorResponse.FieldError> fieldErrors = Collections.emptyList();

        return createErrorResponse(request, HttpStatus.UNPROCESSABLE_ENTITY, exception, exception.getMessage(), fieldErrors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolation(final WebRequest request, final ConstraintViolationException exception) {
        final List<RestErrorResponse.FieldError> fieldErrors = new ArrayList<>();

        for (final ConstraintViolation<?> error : exception.getConstraintViolations()) {
            fieldErrors.add(new RestErrorResponse.FieldError(getPropertyPath(error), error.getMessage()));
        }

        return createErrorResponse(request, HttpStatus.BAD_REQUEST, exception, MensagemConstants.INVALID_FIELDS, fieldErrors);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException exception, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {
        final List<RestErrorResponse.FieldError> fieldErrors = new ArrayList<>();

        for (final org.springframework.validation.FieldError error : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.add(new RestErrorResponse.FieldError(error.getField(), error.getDefaultMessage()));
        }

        return createErrorResponse(request, HttpStatus.BAD_REQUEST, exception, exception.getMessage(), fieldErrors);
    }

    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException exception, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        final List<RestErrorResponse.FieldError> fieldErrors = new ArrayList<>();

        exception.getAllValidationResults().forEach(bindingResult -> {
            for (MessageSourceResolvable error : bindingResult.getResolvableErrors()) {
                fieldErrors.add(new RestErrorResponse.FieldError(bindingResult.getMethodParameter().getParameterName(), error.getDefaultMessage()));
            }
        });

        return createErrorResponse(request, HttpStatus.BAD_REQUEST, exception, exception.getMessage(), fieldErrors);
    }

    @SuppressWarnings("unchecked")
    private <T> ResponseEntity<T> createErrorResponse(final WebRequest request, final HttpStatus status, final Exception exception, final String message,
                                                      final List<RestErrorResponse.FieldError> fieldErrors) {

        HttpHeaders headers = new HttpHeaders();

        return (ResponseEntity<T>) RestErrorResponse.builder()
                .exception(exception)
                .status(status)
                .message(message)
                .fieldErrors(fieldErrors)
                .path(getPath(request))
                .entity(headers);
    }

    private String getPath(final WebRequest request) {
        return request.getDescription(false).substring(4);
    }

    private String getPropertyPath(final ConstraintViolation<?> violation) {
        final Optional<Path.Node> path = StreamSupport.stream(violation.getPropertyPath().spliterator(), false).reduce((first, second) -> second);
        return path.isPresent() ? path.get().toString() : "";
    }

}
