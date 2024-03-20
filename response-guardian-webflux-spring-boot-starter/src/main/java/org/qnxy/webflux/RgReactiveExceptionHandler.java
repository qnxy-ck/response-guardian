package org.qnxy.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rg.common.RespGuardianProperties;
import org.rg.common.data.RgResult;
import org.rg.common.exc.BaseRgExceptionHandler;
import org.rg.common.exc.RgExceptionMappingHandlerRegister;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Qnxy
 */
public class RgReactiveExceptionHandler extends BaseRgExceptionHandler implements ErrorWebExceptionHandler {

    private final RespGuardianProperties respConfig;
    private final ObjectMapper om;

    public RgReactiveExceptionHandler(RespGuardianProperties respConfig, ObjectMapper om, List<RgExceptionMappingHandlerRegister> rgExceptionMappingHandlerRegisters) {
        this.respConfig = respConfig;
        this.om = om;

        resolveExceptionHandler(rgExceptionMappingHandlerRegisters);
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        final ServerHttpRequest request = exchange.getRequest();
        final ServerHttpResponse response = exchange.getResponse();

        final String path = String.format("URL -> [%s %s]", request.getMethod(), request.getPath());

        final RgResult<?> webRgResult = this.exceptionHandlerWithWebResponse(respConfig, (Exception) ex, path, response::setStatusCode);

        final Mono<DataBuffer> dataBufferMono = Mono.fromCallable(() -> this.om.writeValueAsBytes(webRgResult))
                .map(it -> response.bufferFactory().wrap(it));

        return response.writeWith(dataBufferMono);
    }
}
