package org.rg.common;

/**
 * 通用状态信息定义枚举
 *
 * @author Qnxy
 */
public enum RootRespStatusE implements RgStatus {

    COMMON_00000("{0}"),
    COMMON_00001("成功(*^▽^*)"),
    COMMON_00002("失败￣へ￣"),
    COMMON_00003("未知异常_(:з」∠)_"),
    COMMON_00004("未知API资源"),
    COMMON_00005("请求方式不支持"),
    COMMON_00006("请求媒体类型不支持"),


    ;

    RootRespStatusE(String pattern) {
        this.code = this.name();
        this.pattern = pattern;
    }

    private final String code;
    private final String pattern;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getPattern() {
        return pattern;
    }
}
