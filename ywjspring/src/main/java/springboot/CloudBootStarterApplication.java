package springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication
@ComponentScan(basePackages="springboot")
//@MapperScan(basePackages = {"com.arvato.service.lucky.orm.dao"})
@EnableConfigurationProperties()
@EnableScheduling
//@EnableDiscoveryClient
@EnableAsync
@Configuration
@EnableAutoConfiguration
public class CloudBootStarterApplication {
    public static void main(String[] args) {

        SpringApplication.run(CloudBootStarterApplication.class,args);

    }
}