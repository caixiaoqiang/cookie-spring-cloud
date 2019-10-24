package com.cookie;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * author : cxq
 * Date : 2019/1/7
 */

@Configuration
@ConfigurationProperties(prefix = "config")
public class Config {

    private String name ;
    private String tel ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
