package org.rg.demo.controller;

import org.rg.common.RgIgnorePackaging;
import org.rg.common.data.RgResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当前类下部分非 {@link RgResult} 类型均不做处理, 数据原样返回
 *
 * @author Qnxy
 */
@RestController
@RequestMapping("rgIgnorePacking2Test")
public class RgIgnorePacking2TestController {


    /**
     * 数据不做包装, 原样返回
     *
     * @return .
     */
    @GetMapping("/str1")
    @RgIgnorePackaging
    public String str1() {
        return "str1";
    }

    @GetMapping("/int1")
    public int int1() {
        return 10;
    }

    @GetMapping("/rgResult")
    public RgResult<String> rgResult() {
        return RgResult.success("hello world");
    }


}
