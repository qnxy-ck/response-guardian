package org.rg.demo.ex.mapping;

import org.rg.common.data.RgResult;
import org.rg.common.exc.RgExceptionMappingHandler;
import org.rg.demo.enums.MvcDemoStatusE;
import org.rg.demo.ex.CustomEx2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;

/**
 * 自定义异常映射处理
 *
 * @author Qnxy
 */
@Component
public class CustomEx2RgExceptionMappingHandler implements RgExceptionMappingHandler<CustomEx2> {

    @Override
    public RgResult<?> handler(CustomEx2 ex) {
        return RgResult.ofRespStatus(MvcDemoStatusE.CUSTOM_00001, "world");
    }

    /**
     * 对该异常返回自定义 http 状态码
     * <p>
     * 默认返回 {@link HttpStatus#OK}
     */
    @Override
    public HttpStatusCode respHttpCode() {
        return HttpStatus.SEE_OTHER;
    }
}
