package com.zzj.clou.nacosconfigserver.controller;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

@RestController
@RequestMapping("/nacos")
@RefreshScope //实时刷新
public class NacosController {

    @Value(value = "${nacos.test.p1:666}")
    private String username;
    @Value(value = "${test.wdnmd:1111111111}")
    private String wdnmd;

    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;
    @NacosInjected
    private ConfigService configService;

    @GetMapping("/index")
    public String index(){
        String index = "";
        System.out.println(username);
        System.out.println(wdnmd);
        index = wdnmd;
        return index;
    }

    @PostMapping("/change")
    public void changeProperties(HttpServletRequest request){
        String dataId,group,content;
        //默认的是nacosserver
        dataId = request.getParameter("dataId")==null?"nacosserver":request.getParameter("dataId");
        group = request.getParameter("group");
        content = request.getParameter("content");

        String config = "";
        Properties properties = new Properties();
        // 指定配置的 DataID 和 Group
//        String dataId = "${dataId}";
//        String group = "${group}";
//        String content = "connectTimeoutInMills=5000";
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        // 从控制台命名空间管理的"命名空间详情"中拷贝 endpoint、namespace
//        properties.put(PropertyKeyConst.ENDPOINT, "${endpoint}");
//        properties.put(PropertyKeyConst.NAMESPACE, "${namespace}");
        // 推荐使用 RAM 账号的 accessKey、secretKey，
//        properties.put(PropertyKeyConst.ACCESS_KEY, "${accessKey}");
//        properties.put(PropertyKeyConst.SECRET_KEY, "${secretKey}");
        try {
            //自动注入此时是null，暂时没看为什么
            configService = NacosFactory.createConfigService(properties);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        try {
            config = configService.getConfig(dataId,group,6000);
        } catch (NacosException e) {
            e.printStackTrace();
        }
        System.out.println(config);
        try {
            configService.publishConfig(dataId,group,content);//会把原有的配置数据清空
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }
}
