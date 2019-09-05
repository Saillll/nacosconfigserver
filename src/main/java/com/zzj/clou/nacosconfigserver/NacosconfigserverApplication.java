package com.zzj.clou.nacosconfigserver;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import com.alibaba.nacos.api.config.annotation.NacosProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
/**
    //此处若不写明dataId，则会自动去寻找${spring.application.name}.properties的dataId，例如本工程就会去找nacosconfigserver.properties
    //这个在服务启动的时候会打印出来
     2019-09-05 21:57:32.502  INFO 5156 --- [           main] b.c.PropertySourceBootstrapConfiguration : Located property source: CompositePropertySource {name='NACOS', propertySources=[NacosPropertySource {name='nacosconfigserver.properties'}]}
    //@NacosConfigurationProperties(dataId = "nacosserver",groupId = "DEFAULT_GROUP",autoRefreshed=true)


 上面的目前不能这么使用，还没查到为什么，在Spring中可以使用
 <dependency>
 <groupId>com.alibaba.nacos</groupId>
 <artifactId>nacos-spring-context</artifactId>
 <version>${latest.version}</version>
 </dependency>

 然后使用@NacosPropertySource(dataId = "abcd.properties", autoRefreshed = true) 指定dataID
 并且可以使用NacosInjected 注入ConfigServer
2019年9月6日01点00分
 *
 */
public class NacosconfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(NacosconfigserverApplication.class, args);
	}

}
