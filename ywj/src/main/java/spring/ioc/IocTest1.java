package spring.ioc;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class IocTest1 implements IocInterface {
	
	@Value("${isprd}")
	String test;

	@PostConstruct
	public void init(){
		System.out.println(test);
	}

	@Override
	public void test1() {
		// TODO Auto-generated method stub
		
	}
}
