package org.rg.demo.controller;

import org.rg.demo.ex.CustomEx1;
import org.rg.demo.ex.CustomEx2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户自定义异常处理
 *
 * @author Qnxy
 */
@RestController
@RequestMapping("/customEx")
public class CustomExTestController {

    /**
     * 返回自定义异常信息
     * <p>
     * 在 response-guardian.config.web-stack-trace=true 时(默认为true), 返回结果带有当前异常栈信息
     * 关闭则返回 COMMON_00003 -> 未知异常_(:з」∠)_
     */
    @GetMapping("/customEx1")
    public Object customEx1() {
        throw new CustomEx1("我异常了");
    }

    /**
     * 抛出自定义异常映射处理的异常信息
     */
    @GetMapping("/customEx2")
    public Object customEx2() {
        throw new CustomEx2("我也异常了");
    }

 
    
}
