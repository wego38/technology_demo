package netty;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestObj implements Serializable{
	
	private static final long serialVersionUID = 1L;
	String desc;

	public TestObj(String d) {
		this.desc=d;
	}

}
