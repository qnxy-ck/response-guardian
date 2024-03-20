package org.rg.demo.controller;

import org.rg.common.RootRespStatusE;
import org.rg.common.data.RgResult;
import org.rg.demo.enums.MvcDemoStatusE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Qnxy
 */
@RestController
@RequestMapping("/base")
public class BaseTestController {

    /**
     * 返回成功结果
     */
    @GetMapping("/success")
    public RgResult<Void> success() {
        return RgResult.success();
    }

    @GetMapping("/success2")
    public RgResult<Map<String, Integer>> success2() {
        return RgResult.success(Map.of(
                "A-key", 1,
                "B-key", 2,
                "C-key", 3
        ));
    }

    @GetMapping("/success3")
    public RgResult<Void> success3() {
        return RgResult.ofRespStatus(MvcDemoStatusE.CUSTOM_00002, 2);
    }

    /**
     * 返回错误
     */
    @GetMapping("/failure")
    public RgResult<String> failure() {
        return RgResult.failure();
    }

    /**
     * 返回指定状态码的结果
     */
    @GetMapping("/respStatus1")
    public RgResult<String> respStatus1() {
        return RgResult.ofRespStatus(RootRespStatusE.COMMON_00002);
    }


    /**
     * 返回状态信息中带有动态数据的方式
     */
    @GetMapping("/respStatus2")
    public RgResult<String> respStatus2() {
        return RgResult.ofRespStatus(RootRespStatusE.COMMON_00000, "hello world");
    }


    /**
     * 返回状态信息中带有动态数据的方式
     */
    @GetMapping("/respStatus3")
    public RgResult<LocalDateTime> respStatus3() {
        return RgResult.ofRespStatusData(RootRespStatusE.COMMON_00000, LocalDateTime.now(), "hello world");
    }

    /**
     * 抛出异常的方式
     */
    @GetMapping("/fail1")
    public String failure1() {
        // 直接抛出
        // throw new RgBizException(RootRespStatusE.COMMON_00002);

        // 根据状态码进行构建
        throw RootRespStatusE.COMMON_00002.makeException();
    }


    /**
     * 抛出异常并传入动态参数
     */
    @GetMapping("/fail2")
    public RgResult<String> failure2() {
        // 直接抛出
        // throw new RgBizException(RootRespStatusE.COMMON_00000, 1 + 1);

        // 根据状态码进行构建
        throw RootRespStatusE.COMMON_00002.makeException(1 + 1);
    }

}
