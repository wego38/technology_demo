package encryption;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;
import org.springframework.util.DigestUtils;

public class JTestEncryption {

	//@Test
	public void test() throws Exception {
		String inputStr = "简单加密";
		System.err.println("原文:\n" + inputStr);

		byte[] inputData = inputStr.getBytes();
		String code = TestEncryption.encryptBASE64(inputData);

		System.err.println("BASE64加密后:\n" + code);

		byte[] output = TestEncryption.decryptBASE64(code);

		String outputStr = new String(output);

		System.err.println("BASE64解密后:\n" + outputStr);

		// 验证BASE64加密解密一致性
		assertEquals(inputStr, outputStr);

		// 验证MD5对于同一内容加密是否一致
		assertArrayEquals(TestEncryption.encryptMD5(inputData), TestEncryption.encryptMD5(inputData));

		// 验证SHA对于同一内容加密是否一致
		assertArrayEquals(TestEncryption.encryptSHA(inputData), TestEncryption.encryptSHA(inputData));

		String key = TestEncryption.initMacKey();
		System.err.println("Mac密钥:\n" + key);

		// 验证HMAC对于同一内容，同一密钥加密是否一致
		assertArrayEquals(TestEncryption.encryptHMAC(inputData, key), TestEncryption.encryptHMAC(inputData, key));

		BigInteger md5 = new BigInteger(TestEncryption.encryptMD5(inputData));
		System.err.println("MD5:\n" + md5.toString(16));

		BigInteger sha = new BigInteger(TestEncryption.encryptSHA(inputData));
		System.err.println("SHA:\n" + sha.toString(32));

		BigInteger mac = new BigInteger(TestEncryption.encryptHMAC(inputData, inputStr));
		System.err.println("HMAC:\n" + mac.toString(16));
	}
	@Test
	public void test2(){
		System.out.println(new String(DigestUtils.md5DigestAsHex("rou".getBytes())));
	}
}
