package ssl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.junit.Test;

public class JTestSSL {
	private String password = "123456";
	private String alias = "www.ywj.org";
	private String certificatePath = "d:/ywj.cer";
	private String keyStorePath = "d:/ywj.keystore";
	private String clientKeyStorePath = "d:/clientywj.keystore";
	private String clientPassword = "654321";

//	@Test
	public void test() throws Exception {
		System.err.println("��Կ���ܡ���˽Կ����");
		String inputStr = "Ceritifcate";
		byte[] data = inputStr.getBytes();

		byte[] encrypt = TestSSL.encryptByPublicKey(data,
				certificatePath);

		byte[] decrypt = TestSSL.decryptByPrivateKey(encrypt,
				keyStorePath, alias, password);
		String outputStr = new String(decrypt);

		System.err.println("����ǰ: " + inputStr + "\n\r" + "���ܺ�: " + outputStr);

		// ��֤����һ��
		assertArrayEquals(data, decrypt);

		// ��֤֤����Ч
		assertTrue(TestSSL.verifyCertificate(certificatePath));

	}

//	@Test
	public void testSign() throws Exception {
		System.err.println("˽Կ���ܡ�����Կ����");

		String inputStr = "sign";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = TestSSL.encryptByPrivateKey(data,
				keyStorePath, alias, password);

		byte[] decodedData = TestSSL.decryptByPublicKey(encodedData,
				certificatePath);

		String outputStr = new String(decodedData);
		System.err.println("����ǰ: " + inputStr + "\n\r" + "���ܺ�: " + outputStr);
		assertEquals(inputStr, outputStr);

		System.err.println("˽Կǩ��������Կ��֤ǩ��");
		// ����ǩ��
		String sign = TestSSL.sign(encodedData, keyStorePath, alias,
				password);
		System.err.println("ǩ��:\r" + sign);

		// ��֤ǩ��
		boolean status = TestSSL.verify(encodedData, sign,
				certificatePath);
		System.err.println("״̬:\r" + status);
		assertTrue(status);

	}

	@Test
	public void testHttps() throws Exception {
		URL url = new URL("https://www.ywj.org/ywj/");
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

		conn.setDoInput(true);
		conn.setDoOutput(true);

		TestSSL.configSSLSocketFactory(conn, clientPassword,
				clientKeyStorePath, clientKeyStorePath);

		InputStream is = conn.getInputStream();

		int length = conn.getContentLength();

		DataInputStream dis = new DataInputStream(is);
		byte[] data = new byte[length];
		dis.readFully(data);

		dis.close();
		System.err.println(new String(data));
		conn.disconnect();
	}
}
