package org.sipc.controlserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * restTemplate的配置类
 *
 * @author tzih
 * @version 1.0
 * @since 2023/4/11 9:29
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 获取restTemplate的实例对象
     *
     * @return 返回restTemplate的实例对象
     */
    @Bean
    public RestTemplate restTemplate() {

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(10*1000);
        requestFactory.setReadTimeout(10*1000);

        return new RestTemplate(requestFactory);
    }

}
