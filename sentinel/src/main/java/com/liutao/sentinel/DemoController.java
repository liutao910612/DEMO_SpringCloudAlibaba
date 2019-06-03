package com.liutao.sentinel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 *
 * @author: LIUTAO
 * @Date: Created in 2019/6/3  16:24
 * @Modified By:
 */
@Slf4j
@RestController
public class DemoController {

    @GetMapping("/hello")
    public String hello(){
        return "liutao";
    }
}

