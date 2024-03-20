package org.rg.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置信息
 *
 * @author Qnxy
 */

@ConfigurationProperties(prefix = "response-guardian.config")
@Configuration
public class RespGuardianProperties {


    /**
     * 开启时web返回结果包含异常栈错误信息
     * 仅在未处理异常时返回, 这在调试时非常有帮助
     */
    private boolean webStackTrace = true;

    /**
     * 是否开启异常处理
     */
    private boolean globalExHandler = true;

    /**
     * 是否开启返回结果简化统一处理
     */
    private boolean responseAdvice = true;


    public boolean isWebStackTrace() {
        return webStackTrace;
    }

    public void setWebStackTrace(boolean webStackTrace) {
        this.webStackTrace = webStackTrace;
    }

    public boolean isGlobalExHandler() {
        return globalExHandler;
    }

    public void setGlobalExHandler(boolean globalExHandler) {
        this.globalExHandler = globalExHandler;
    }

    public boolean isResponseAdvice() {
        return responseAdvice;
    }

    public void setResponseAdvice(boolean responseAdvice) {
        this.responseAdvice = responseAdvice;
    }


}
