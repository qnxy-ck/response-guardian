package org.rg.common.exc;

import org.rg.common.RespGuardianProperties;
import org.rg.common.RootRespStatusE;
import org.rg.common.data.RgResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatusCode;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import static org.rg.common.data.RgResult.ofBizException;
import static org.rg.common.data.RgResult.ofRespStatus;

/**
 * 公共异常处理逻辑
 *
 * @author Qnxy
 */
public abstract class BaseRgExceptionHandler implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(BaseRgExceptionHandler.class);
    private static final Map<Class<? extends Exception>, RgExceptionMappingHandler<?>> EXCEPTION_MAPPING_HANDLER_MAP = new ConcurrentHashMap<>();


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("全局异常处理已加载...");
    }

    /**
     * 打印日志信息
     */
    protected void outLogInfo(Exception exception, String path) {

        if (exception instanceof RgBizException || EXCEPTION_MAPPING_HANDLER_MAP.containsKey(exception.getClass())) {
            log.error("当前请求发生错误: {} -- {}", path, exception.getMessage());
        } else {
            log.error("当前请求发生错误: {}", path, exception);
        }
    }

    /**
     * 处理异常并返回对应的 {@link RgResult}
     * 根据配置打印日志, 返回异常信息等
     *
     * @param respConfig         配置信息
     * @param exception          待处理的异常信息
     * @param path               当前请求路径信息
     * @param httpStatusConsumer 当前请求返回状态处理
     * @return .
     */
    protected RgResult<?> exceptionHandlerWithWebResponse(RespGuardianProperties respConfig, Exception exception, String path, Consumer<HttpStatusCode> httpStatusConsumer) {

        // 打印异常信息
        outLogInfo(exception, path);

        // 优先执行自定义异常信息
        final RgResult<?> webRgResult = Optional.ofNullable(EXCEPTION_MAPPING_HANDLER_MAP.get(exception.getClass()))
                .flatMap(it -> Optional.ofNullable(it.callHandler(exception, httpStatusConsumer)))
                .orElse(null);

        if (webRgResult != null) {
            return webRgResult;
        }

        // 处理业务异常信息
        if (exception instanceof RgBizException rgBizException) {
            return ofBizException(rgBizException);
        }

        // 检查是否开启了未处理异常栈信息返回, 开启则返回异常栈信息
        if (respConfig.isWebStackTrace() && !EXCEPTION_MAPPING_HANDLER_MAP.containsKey(Exception.class)) {
            final String stackMsg = exceptionStackMsg(exception);

            return ofRespStatus(RootRespStatusE.COMMON_00000, new Object[]{stackMsg});
        }

        // 未知异常
        return ofRespStatus(RootRespStatusE.COMMON_00003);
    }

    /**
     * 解析用户自定义异常映射信息
     *
     * @param rgExceptionMappingHandlerRegisters 用户自定义的异常处理及映射信息
     */
    protected static void resolveExceptionHandler(List<RgExceptionMappingHandlerRegister> rgExceptionMappingHandlerRegisters) {
        Optional.ofNullable(rgExceptionMappingHandlerRegisters)
                .stream()
                .flatMap(Collection::stream)
                .forEach(it -> it.register(EXCEPTION_MAPPING_HANDLER_MAP));
    }

    /**
     * 获取异常栈信息
     *
     * @param throwable ex
     * @return 异常栈信息
     */
    private static String exceptionStackMsg(Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();

        throwable.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
