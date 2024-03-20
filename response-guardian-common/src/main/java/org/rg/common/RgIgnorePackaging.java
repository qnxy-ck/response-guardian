package org.rg.common;

import org.rg.common.data.RgResult;

import java.lang.annotation.*;

/**
 * 忽略返回结果简化包装处理
 * <p>
 * 当接口返回非 {@link RgResult <>} 时, 忽略对其的统一包装.
 *
 * @author Qnxy
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RgIgnorePackaging {

}
