package com.liutao.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 程序启动主类
 *
 * @author: LIUTAO
 * @Date: Created in 2019/5/30  16:25
 * @Modified By:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class NacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class,args);
    }
}


