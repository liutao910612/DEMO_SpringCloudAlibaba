package com.liutao.nacos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * 测试controller
 *
 * @author: LIUTAO
 * @Date: Created in 2019/5/30  16:27
 * @Modified By:
 */
@Slf4j
@RestController
@RefreshScope
public class DemoController {

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "hello," + name;
    }

    /**
     * 这里模仿服务消费，就不单独创建应用了
     *
     * @return
     */
    @GetMapping("/test")
    public String test() {

        //通过spring cloud common中的负载均衡接口选取服务提供节点实现服务调用
        ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos");
        String url = serviceInstance.getUri() + "/hello?name=liutao";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        return "Invoke:" + url + ",return : " + result;
    }

    /**#######################################消费服务的示例#################################################*/

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/consumer/demo")
    public String testConsumer(@RequestParam("type") String type) {
        String result = null;

        switch (type) {
            case "restTemplate":
                result = "Return : " + testRestTemplate();
                break;
            case "webClient":
                result = "Return : " + testWebClient();
                break;
            case "feign":
                result = "Return : " + testFeign();
                break;
        }

        return result;
    }

    /**
     * (1)使用RestTemplate来消费
     * <p>
     * 在真正调用的时候，Spring Cloud会将请求拦截下来，然后通过负载均衡器选出节点，并替换服务名部分为具体的ip和端口，
     * 从而实现基于服务名的负载均衡调用。
     *
     * @return
     */
    private String testRestTemplate() {
        String result = restTemplate.getForObject("http://alibaba-nacos/hello?name=liutao", String.class);
        return result;
    }

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * 使用WebClient来消费
     *
     * @return
     */
    private String testWebClient() {
        Mono<String> result = webClientBuilder.build()
                .get()
                .uri("http://alibaba-nacos/hello?name=liutao")
                .retrieve()
                .bodyToMono(String.class);
        return result.block();
    }

    @Autowired
    private Client client;

    /**
     * 使用WebClient来消费
     *
     * @return
     */
    private String testFeign() {
        String result = client.hello("liutao");
        return result;
    }

    /**
     * ############################################nacos配置中心######################################################
     */
    @Value("${liutaospace.title:}")
    private String title;

    @GetMapping("/config/demo")
    public String testConfig() {
        return title;
    }
}


