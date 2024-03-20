package org.rg.webmvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rg.common.RgIgnorePackaging;
import org.rg.common.data.RgResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 简化非 {@link RgResult} 结果包装并返回
 *
 * @author Qnxy
 */
@RestControllerAdvice
final class ResponseGuardianAdvice implements ResponseBodyAdvice<Object>, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(ResponseGuardianAdvice.class);

    private final ObjectMapper om;

    public ResponseGuardianAdvice(ObjectMapper om) {
        this.om = om;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {

        // 如果请求 controller 类/方法上包含忽略注解, 则不进行包装处理
        return methodParameter.getMethodAnnotation(RgIgnorePackaging.class) == null
                && methodParameter.getDeclaringClass().getAnnotation(RgIgnorePackaging.class) == null;

    }

    @Override
    public Object beforeBodyWrite(Object body,
                                  MethodParameter methodParameter,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response
    ) {

        if (selectedConverterType.equals(StringHttpMessageConverter.class)) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            try {
                return om.writeValueAsString(body != null ? RgResult.success(body) : RgResult.success());
            } catch (JsonProcessingException e) {
                return RgResult.failure();
            }
        }

        if (body instanceof RgResult<?>) {
            return body;
        }

        return RgResult.success(body);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("返回结果简化处理已加载...");
    }
}
