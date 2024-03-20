package org.rg.common.exc;

import org.rg.common.data.RgResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.function.Consumer;

/**
 * 自定义异常处理及异常映射返回结果
 * <p>
 * 用户可实现该接口并注入容器以实现自定义异常处理
 *
 * @author Qnxy
 */
@FunctionalInterface
public interface RgExceptionMappingHandler<E extends Exception> {

    /**
     * 当发生泛型指定异常时接口返回的状态码
     * 默认为 200 即发生错误正常返回
     * <p>
     * 如果需要为特殊异常自定义状态码, 直接实现该接口并指定异常重写该方法即可
     *
     * @return 发生错误时返回的HTTP状态码
     */
    default HttpStatusCode respHttpCode() {
        return HttpStatus.OK;
    }

    /**
     * 发生异常时指定泛型异常的处理方法, 用户可根据对应异常返回指定结果信息
     *
     * @param ex 当前异常
     * @return .
     */
    RgResult<?> handler(E ex);

    /**
     * 不要尝试重写它, 可能会影响正常使用
     */
    default RgResult<?> callHandler(Exception ex, Consumer<HttpStatusCode> httpStatusConsumer) {
        httpStatusConsumer.accept(this.respHttpCode());

        //noinspection unchecked
        return this.handler(((E) ex));
    }

}
