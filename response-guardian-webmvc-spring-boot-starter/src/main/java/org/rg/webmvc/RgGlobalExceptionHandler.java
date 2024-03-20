package org.rg.webmvc;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rg.common.RespGuardianProperties;
import org.rg.common.data.RgResult;
import org.rg.common.exc.BaseRgExceptionHandler;
import org.rg.common.exc.RgExceptionMappingHandlerRegister;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * 全局异常处理
 * 支持用户自定义错误异常处理
 *
 * @author Qnxy
 */
@RestControllerAdvice
final class RgGlobalExceptionHandler extends BaseRgExceptionHandler {

    private final RespGuardianProperties respConfig;


    public RgGlobalExceptionHandler(RespGuardianProperties respConfig, List<RgExceptionMappingHandlerRegister> rgExceptionMappingHandlerRegisters) {
        this.respConfig = respConfig;

        resolveExceptionHandler(rgExceptionMappingHandlerRegisters);
    }


    @ExceptionHandler
    public RgResult<?> handler(Exception exception, HttpServletRequest request, HttpServletResponse response) {

        final String path = String.format("URL -> [%s %s]", request.getMethod(), request.getRequestURI());
        return this.exceptionHandlerWithWebResponse(respConfig, exception, path, it -> response.setStatus(it.value()));
    }


}
