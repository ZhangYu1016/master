package com.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.log4j.Logger;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
@EnableDiscoveryClient
@RestController
public class ConfigServerApplication {

	private static Logger logger = Logger.getLogger(ConfigServerApplication.class);

	@Autowired
	private ServerProperties serverProperties;
	@Autowired
	private Environment enviroment;
	@Autowired
	private ConfigurableEnvironment ConfigurableEnvironment;

	 public static void main(String[] args) {
	       SpringApplication.run(ConfigServerApplication.class, args);
	 }
	 @RequestMapping(value = "/test")
	 public Integer test() {
		 return Integer.valueOf(enviroment.getProperty("server.port"));
	 }
}

