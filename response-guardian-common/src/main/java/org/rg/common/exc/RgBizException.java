package org.rg.common.exc;

import org.rg.common.RgStatus;

/**
 * 公共业务异常
 * <p>
 * 仅可使用 {@link RgStatus} 来创建该异常
 *
 * @author Qnxy
 */
public class RgBizException extends RuntimeException {

    private final String code;


    public RgBizException(RgStatus status) {
        super(status.formattedInfo());
        this.code = status.getCode();
    }

    public RgBizException(RgStatus status, Object... args) {
        super(status.formattedInfo(args));
        this.code = status.getCode();
    }

    public RgBizException(RgStatus status, Throwable cause, Object... args) {
        super(status.formattedInfo(args), cause);
        this.code = status.getCode();
    }

    public String getCode() {
        return code;
    }
}
