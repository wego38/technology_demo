package spring.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = "spring", excludeFilters = @ComponentScan.Filter(Controller.class))
@EnableAspectJAutoProxy
//@EnableJpaRepositories(basePackages = "spring", includeFilters = @ComponentScan.Filter(Repository.class))
//@EnableSpringDataWebSupport
//@ImportResource(value = {"classpath*:spring-config.xml", "classpath*:spring-security.xml", "classpath*:config/applicationContext.xml"})
//@ImportResource(value = {"classpath*:spring-config.xml", "classpath*:spring-security.xml"})
public class AppConfig {

	@Bean
	public PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer(){
		PropertyPlaceholderConfigurer config=new PropertyPlaceholderConfigurer();
		config.setLocation(new DefaultResourceLoader().getResource("classpath:common.properties"));
		return config;
	}
    
}
