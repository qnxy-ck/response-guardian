package org.rg.common.exc;

import java.util.Map;

/**
 * 自定义异常处理的另一种处理方式, 可以一次性注册多个错误处理
 * 而无需使用 {@link RgExceptionMappingHandler} 来进行单个异常进行指定
 * 默认已实现 {@link DefaultRgExceptionMappingHandlerRegister}, 但你仍可实现该接口进行批量错误处理映射
 *
 * @author Qnxy
 */
@FunctionalInterface
public interface RgExceptionMappingHandlerRegister {

    /**
     * 注册用户自定义异常处理
     *
     * @param registerMap .
     */
    void register(Map<Class<? extends Exception>, RgExceptionMappingHandler<?>> registerMap);

}
