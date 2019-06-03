package com.liutao.nacos;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Feign客户端
 *
 * @author: LIUTAO
 * @Date: Created in 2019/6/3  13:56
 * @Modified By:
 */
@FeignClient("alibaba-nacos")
public interface Client {

    @GetMapping("/hello")
    String hello(@RequestParam("name") String name);
}
