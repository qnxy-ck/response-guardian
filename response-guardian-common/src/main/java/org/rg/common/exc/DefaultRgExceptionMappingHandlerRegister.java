package org.rg.common.exc;

import org.rg.common.RootRespStatusE;
import org.springframework.core.ResolvableType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.rg.common.data.RgResult.ofRespStatus;

/**
 * 默认基础异常映射处理注册
 * 用户可以重新实现 {@link RgExceptionMappingHandlerRegister} 接口来实现自定义异常注册
 *
 * @author Qnxy
 */
public class DefaultRgExceptionMappingHandlerRegister implements RgExceptionMappingHandlerRegister {

    private final List<RgExceptionMappingHandler<?>> rgExceptionMappingHandlers;
    private final Map<Class<? extends Exception>, RgExceptionMappingHandler<?>> extExceptionMap;


    public DefaultRgExceptionMappingHandlerRegister(
            List<RgExceptionMappingHandler<?>> rgExceptionMappingHandlers,
            Map<Class<? extends Exception>, RgExceptionMappingHandler<?>> extExceptionMap
    ) {
        this.rgExceptionMappingHandlers = rgExceptionMappingHandlers;
        this.extExceptionMap = extExceptionMap;
    }

    @Override
    public void register(Map<Class<? extends Exception>, RgExceptionMappingHandler<?>> registerMap) {
        registerDefaultExMappingHandler(registerMap);

        registerMap.putAll(this.extExceptionMap);

        // 用户自定义相关错误处理
        customExceptionMappingHandler(registerMap, rgExceptionMappingHandlers);
    }

    /**
     * 默认错误处理映射
     */
    private static void registerDefaultExMappingHandler(Map<Class<? extends Exception>, RgExceptionMappingHandler<?>> registerMap) {
        // 请求方式错误
        registerMap.put(HttpRequestMethodNotSupportedException.class, (RgExceptionMappingHandler<HttpRequestMethodNotSupportedException>) ex -> ofRespStatus(RootRespStatusE.COMMON_00005));
        registerMap.put(HttpMediaTypeNotSupportedException.class, (RgExceptionMappingHandler<HttpMediaTypeNotSupportedException>) ex -> ofRespStatus(RootRespStatusE.COMMON_00005));

    }

    private static void customExceptionMappingHandler(Map<Class<? extends Exception>, RgExceptionMappingHandler<?>> registerMap, Collection<RgExceptionMappingHandler<?>> rgExceptionMappingHandlers) {
        Optional.ofNullable(rgExceptionMappingHandlers)
                .stream()
                .flatMap(Collection::stream)
                .forEach(it -> {
                    ResolvableType generic = ResolvableType.forInstance(it).getInterfaces()[0].getGeneric(0);
                    //noinspection unchecked
                    registerMap.put((Class<? extends Exception>) generic.getRawClass(), it);
                });
    }

}
