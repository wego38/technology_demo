package encryption;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class JTestDHEncryption {

	@Test
	public void test() throws Exception {
		// 生成甲方密钥对儿
		Map<String, Object> aKeyMap = TestDHEncryption.initKey();
		String aPublicKey = TestDHEncryption.getPublicKey(aKeyMap);
		String aPrivateKey = TestDHEncryption.getPrivateKey(aKeyMap);

		System.err.println("甲方公钥:\r" + aPublicKey);
		System.err.println("甲方私钥:\r" + aPrivateKey);
		
		// 由甲方公钥产生本地密钥对儿
		Map<String, Object> bKeyMap = TestDHEncryption.initKey(aPublicKey);
		String bPublicKey = TestDHEncryption.getPublicKey(bKeyMap);
		String bPrivateKey = TestDHEncryption.getPrivateKey(bKeyMap);
		
		System.err.println("乙方公钥:\r" + bPublicKey);
		System.err.println("乙方私钥:\r" + bPrivateKey);
		
		String aInput = "abc ";
		System.err.println("原文: " + aInput);

		// 由甲方公钥，乙方私钥构建密文
		byte[] aCode = TestDHEncryption.encrypt(aInput.getBytes(), aPublicKey,
				bPrivateKey);

		// 由乙方公钥，甲方私钥解密
		byte[] aDecode = TestDHEncryption.decrypt(aCode, bPublicKey, aPrivateKey);
		String aOutput = (new String(aDecode));

		System.err.println("解密: " + aOutput);

		assertEquals(aInput, aOutput);

		System.err.println(" ===============反过来加密解密================== ");
		String bInput = "def ";
		System.err.println("原文: " + bInput);

		// 由乙方公钥，甲方私钥构建密文
		byte[] bCode = TestDHEncryption.encrypt(bInput.getBytes(), bPublicKey,
				aPrivateKey);

		// 由甲方公钥，乙方私钥解密
		byte[] bDecode = TestDHEncryption.decrypt(bCode, aPublicKey, bPrivateKey);
		String bOutput = (new String(bDecode));

		System.err.println("解密: " + bOutput);

		assertEquals(bInput, bOutput);
	}

}
