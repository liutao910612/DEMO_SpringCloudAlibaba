package com.liutao.nacos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 测试controller
 *
 * @author: LIUTAO
 * @Date: Created in 2019/5/30  16:27
 * @Modified By:
 */
@RestController
public class DemoController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/hello")
    public String hello(@RequestParam String name){
        return "hello,"+name;
    }

    /**
     * 这里模仿服务消费，就不单独创建应用了
     * @return
     */
    @GetMapping("/test")
    public String test(){

        //通过spring cloud common中的负载均衡接口选取服务提供节点实现服务调用
        ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos");
        String url =  serviceInstance.getUri() + "/hello?name=liutao";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url,String.class);
        return "Invoke:"+url + ",return : "+result;
    }
}
