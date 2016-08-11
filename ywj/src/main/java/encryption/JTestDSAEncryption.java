package encryption;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class JTestDSAEncryption {

	@Test
	public void test() throws Exception {
		String inputStr = "abc";
		byte[] data = inputStr.getBytes();

		// 构建密钥
		Map<String, Object> keyMap = TestDSAEncryption.initKey();

		// 获得密钥
		String publicKey = TestDSAEncryption.getPublicKey(keyMap);
		String privateKey = TestDSAEncryption.getPrivateKey(keyMap);

		System.err.println("公钥:\r" + publicKey);
		System.err.println("私钥:\r" + privateKey);

		// 产生签名
		String sign = TestDSAEncryption.sign(data, privateKey);
		System.err.println("签名:\r" + sign);

		// 验证签名
		boolean status = TestDSAEncryption.verify(data, publicKey, sign);
		System.err.println("状态:\r" + status);
		assertTrue(status);

	}

}
