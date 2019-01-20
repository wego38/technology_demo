package springboot;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages="springboot")
//@MapperScan(basePackages = {"com.arvato.service.lucky.orm.dao"})
@EnableConfigurationProperties()
@EnableScheduling
@EnableAsync
public class BootStarterApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext context=new SpringApplicationBuilder(BootStarterApplication.class)
                .web(WebApplicationType.SERVLET).run(args);

    }
}