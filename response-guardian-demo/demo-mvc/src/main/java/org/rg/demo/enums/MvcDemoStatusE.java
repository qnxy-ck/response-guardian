package org.rg.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.rg.common.RgStatus;
import org.rg.common.data.RgResult;

/**
 * 自定义状态码, 所有自定义的状态码需要实现 {@link RgStatus}
 * <p>
 * 并且 {@link RgResult} 静态创建函数要求实现类必须是枚举
 * 使用枚举的方式可以有效避免状态码的重复, 可以使用不同开头的枚举字段进行业务隔离
 *
 * @author Qnxy
 */
@Getter
@RequiredArgsConstructor
public enum MvcDemoStatusE implements RgStatus {

    CUSTOM_00001("hello {0}!!!"),
    CUSTOM_00002("1 + 1 = {0}!!!"),
    ;

    private final String pattern;


    @Override
    public String getCode() {
        return this.name();
    }
}
