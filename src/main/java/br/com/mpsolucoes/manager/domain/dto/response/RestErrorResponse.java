package br.com.mpsolucoes.manager.domain.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(value = Include.NON_EMPTY)
public class RestErrorResponse {

    @Builder.Default
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String type;

    private final int status;

    private final String error;

    private final String message;

    @JsonInclude(value = Include.NON_NULL)
    private final List<FieldError> fieldErrors;

    private final String detail;

    private final String path;

    private final String help;

    public static class RestErrorResponseBuilder {

        public RestErrorResponseBuilder status(final HttpStatus status) {
            this.status = status.value();
            error = status.getReasonPhrase();

            return this;
        }

        public RestErrorResponseBuilder exception(final Exception exception) {
            type = exception.getClass().getSimpleName();
            error = exception.getMessage();
            message = exception.getMessage();

            if (exception.getCause() != null) {
                detail = exception.getCause().toString();
            } else if (exception.getMessage() != null && !exception.getMessage().equals(exception.getLocalizedMessage())) {
                detail = exception.getLocalizedMessage();
            }

            return this;
        }

        public ResponseEntity<RestErrorResponse> entity(HttpHeaders headers) {
            return ResponseEntity.status(status).headers(headers).body(build());
        }
    }

    public record FieldError(String field, String error) {

    }
}