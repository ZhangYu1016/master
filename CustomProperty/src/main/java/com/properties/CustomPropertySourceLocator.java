package com.properties;

import org.apache.log4j.Logger;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Map;

@Configuration
@RefreshScope
public class CustomPropertySourceLocator implements PropertySourceLocator {

    private static Logger logger = Logger.getLogger(CustomPropertySourceLocator.class);

    private Map<String, Object> map = null;
    public CustomPropertySourceLocator(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public PropertySource<?> locate(Environment environment) {
        logger.info("开始初始化配置========================");
        map.put("test",111222);
        if(map != null) {
            logger.info("初始化成功==========================");
            return new MapPropertySource("myProperty", map);
        }
        return null;
    }
}
