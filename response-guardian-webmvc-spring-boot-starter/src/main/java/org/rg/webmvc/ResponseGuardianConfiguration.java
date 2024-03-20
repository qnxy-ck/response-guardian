package org.rg.webmvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.rg.common.RespGuardianProperties;
import org.rg.common.RootRespStatusE;
import org.rg.common.exc.DefaultRgExceptionMappingHandlerRegister;
import org.rg.common.exc.RgExceptionMappingHandler;
import org.rg.common.exc.RgExceptionMappingHandlerRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;

import static org.rg.common.data.RgResult.ofRespStatus;

/**
 * 配置信息-自动注入
 * 
 * @author Qnxy
 */
final class ResponseGuardianConfiguration {

    /**
     * 注入配置相关信息
     */
    @Bean
    RespGuardianProperties respGuardianProperties() {
        return new RespGuardianProperties();
    }

    /**
     * 返回结果简化统一包装处理
     * 如不需要根据配置关闭即可
     * 默认开启
     */
    @Bean
    @ConditionalOnProperty(value = "response-guardian.config.enable-resp-advice", havingValue = "true", matchIfMissing = true)
    ResponseGuardianAdvice responseGuardianAdvice(ObjectMapper om) {
        return new ResponseGuardianAdvice(om);
    }

    /**
     * 全局异常处理
     * 如不需要根据配置关闭即可
     * 默认开启
     *
     * @param respGuardianProperties             配置信息
     * @param rgExceptionMappingHandlerRegisters 自定义错误映射处理
     */
    @Bean
    @ConditionalOnProperty(value = "response-guardian.config.enable-global-ex-handler", havingValue = "true", matchIfMissing = true)
    RgGlobalExceptionHandler globalExceptionHandler(
            RespGuardianProperties respGuardianProperties,
            List<RgExceptionMappingHandlerRegister> rgExceptionMappingHandlerRegisters
    ) {
        return new RgGlobalExceptionHandler(respGuardianProperties, rgExceptionMappingHandlerRegisters);
    }


    /**
     * 处理用户自义错误映射处理
     *
     * @param rgExceptionMappingHandlers 注入用户自定义错误信息
     */
    @Bean
    @ConditionalOnBean(RgGlobalExceptionHandler.class)
    DefaultRgExceptionMappingHandlerRegister defaultExceptionMappingHandlerRegister(@Autowired(required = false) List<RgExceptionMappingHandler<?>> rgExceptionMappingHandlers) {
        return new DefaultRgExceptionMappingHandlerRegister(
                rgExceptionMappingHandlers,
                Map.of(
                        // 未知资源
                        NoResourceFoundException.class, (RgExceptionMappingHandler<NoResourceFoundException>) ex -> ofRespStatus(RootRespStatusE.COMMON_00004)
                )
        );
    }


}
