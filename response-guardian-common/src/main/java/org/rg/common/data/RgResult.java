package org.rg.common.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.rg.common.exc.RgBizException;
import org.rg.common.RgStatus;
import org.rg.common.RootRespStatusE;

/**
 * 统一返回结果包装
 *
 * @author Qnxy
 */
public final class RgResult<DATA> {

    /**
     * 返回状态码
     */
    private final String statusCode;

    /**
     * 对应状态信息
     */
    private final String statusInfo;

    /**
     * 当前时间戳
     */
    private final String timestamp;

    /**
     * 实际数据信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final DATA data;

    private <E extends Enum<E> & RgStatus> RgResult(E status, DATA data, Object... args) {
        this.statusCode = status.getCode();
        this.statusInfo = status.formattedInfo(args);
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.data = data;
    }

    private RgResult(RgBizException rgBizException) {
        this.statusCode = rgBizException.getCode();
        this.statusInfo = rgBizException.getMessage();
        this.timestamp = String.valueOf(System.currentTimeMillis());
        this.data = null;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public DATA getData() {
        return data;
    }

    public static <D> RgResult<D> success() {
        return new RgResult<>(RootRespStatusE.COMMON_00001, null);
    }

    public static <D> RgResult<D> success(D data) {
        return new RgResult<>(RootRespStatusE.COMMON_00001, data);
    }

    public static <D> RgResult<D> failure() {
        return new RgResult<>(RootRespStatusE.COMMON_00002, null);
    }

    public static <D, E extends Enum<E> & RgStatus> RgResult<D> ofRespStatus(E respStatus) {
        return new RgResult<>(respStatus, null);
    }

    public static <D, E extends Enum<E> & RgStatus> RgResult<D> ofRespStatus(E respStatus, Object... args) {
        return new RgResult<>(respStatus, null, args);
    }

    public static <D, E extends Enum<E> & RgStatus> RgResult<D> ofRespStatusData(E respStatus, D data, Object... args) {
        return new RgResult<>(respStatus, data, args);
    }


    public static <D> RgResult<D> ofBizException(RgBizException rgBizException) {
        return new RgResult<>(rgBizException);
    }


}
