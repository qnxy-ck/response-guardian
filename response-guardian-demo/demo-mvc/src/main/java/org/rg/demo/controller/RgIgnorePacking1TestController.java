package org.rg.demo.controller;

import org.rg.common.RgIgnorePackaging;
import org.rg.common.data.RgResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 当前类下所有非 {@link RgResult} 类型均不做处理, 数据原样返回
 *
 * @author Qnxy
 */
@RestController
@RequestMapping("rgIgnorePacking1Test")
@RgIgnorePackaging
public class RgIgnorePacking1TestController {


    @GetMapping("/str1")
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
