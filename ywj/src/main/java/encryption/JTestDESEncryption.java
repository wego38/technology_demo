package encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class JTestDESEncryption {

	@Test
	public void test() throws Exception {
		String inputStr = "DES";
		String key = TestDESEncryption.initKey();
		System.err.println("原文:\t" + inputStr);

		System.err.println("密钥:\t" + key);

		byte[] inputData = inputStr.getBytes();
		inputData = TestDESEncryption.encrypt(inputData, key);

		System.err.println("加密后:\t" + TestDESEncryption.encryptBASE64(inputData));

		byte[] outputData = TestDESEncryption.decrypt(inputData, key);
		String outputStr = new String(outputData);

		System.err.println("解密后:\t" + outputStr);

		assertEquals(inputStr, outputStr);
	}
}
