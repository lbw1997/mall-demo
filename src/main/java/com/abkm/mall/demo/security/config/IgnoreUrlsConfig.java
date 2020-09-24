package com.abkm.mall.demo.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * description: 白名单路径配置类 <br>
 * date: 2020/9/24 18:52 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig {

    private List<String> urls = new ArrayList<>();
}
