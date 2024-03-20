package org.rg.common;

import org.rg.common.data.RgResult;
import org.rg.common.exc.RgBizException;

/**
 * 返回状态信息
 * <p>
 * 所有自定义的状态码需要实现 {@link RgStatus}
 * 并且 {@link RgResult} 静态创建函数要求实现类必须是枚举
 * 使用枚举的方式可以有效避免状态码的重复, 可以使用不同开头的枚举字段进行业务隔离
 *
 * @author Qnxy
 */
public interface RgStatus {

    /**
     * 状态码
     */
    String getCode();


    /**
     * 状态信息
     */
    String getPattern();

    default String formattedInfo(Object... args) {
        return StatusMessageFormatCache.obtainMessageFormat(this.getPattern())
                .format(args);
    }


    default void throwEx(Object... args) {
        throw new RgBizException(this, args);
    }

    default RgBizException makeException(Object... args) {
        return new RgBizException(this, args);
    }


}
