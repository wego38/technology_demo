package spring.ioc;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class IocTest2 {
//	@Autowired
//	IocTest1 iocTest1;
//	@Autowired
//	IocTest3 iocTest3;
//	@Autowired
//	IocTest4 iocTest4;
//	@Autowired
//	IocTest4 iocTest5;
	@Autowired
	@Qualifier("iocTest1")
	IocInterface iocTest1;
	@Autowired
	@Qualifier("iocTest1")
	IocTest1 iocTest3;
//	@Autowired
//	@Qualifier("iocTest4")
//	IocTest1 iocTest4;
//	@Qualifier("iocTest1")
//	IocTest1 iocTest6;
//	@Autowired
//	@Qualifier("iocTest5")
//	IocTest5 iocTest55;

	@PostConstruct
	public void init(){
		System.out.println("2");
	}
}
