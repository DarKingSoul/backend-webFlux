package com.mitocode.Exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebException extends AbstractErrorWebExceptionHandler {

    public WebException(ErrorAttributes errorAttributes, WebProperties.Resources resources, ApplicationContext applicationContext,
                        ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest serverRequest) {
        Map<String, Object> generalError = getErrorAttributes(serverRequest, ErrorAttributeOptions.defaults());
        Map<String, Object> customError = new HashMap<>();

        HttpStatus status  = HttpStatus.INTERNAL_SERVER_ERROR;
        int statusCode = Integer.parseInt(String.valueOf(generalError.get("status")));
        Throwable error = getError(serverRequest);

        switch (statusCode) {
            case 400, 422 -> {
                status = HttpStatus.BAD_REQUEST;
                customError.put("message", error.getMessage());
                customError.put("status", status.value());
                customError.put("error", status.getReasonPhrase());
                customError.put("path", generalError.get("path"));
                customError.put("timestamp", generalError.get("timestamp"));
            }
            case 404 -> {
                status = HttpStatus.NOT_FOUND;
                customError.put("message", error.getMessage());
                customError.put("status", status.value());
                customError.put("error", status.getReasonPhrase());
                customError.put("path", generalError.get("path"));
                customError.put("timestamp", generalError.get("timestamp"));
            }
            default -> {
                customError.put("message", "Internal Server Error" + error.getMessage());
                customError.put("status", status.value());
                customError.put("error", status.getReasonPhrase());
                customError.put("path", generalError.get("path"));
                customError.put("timestamp", generalError.get("timestamp"));
            }
        }

        return ServerResponse.status(status)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(customError));
    }

}
