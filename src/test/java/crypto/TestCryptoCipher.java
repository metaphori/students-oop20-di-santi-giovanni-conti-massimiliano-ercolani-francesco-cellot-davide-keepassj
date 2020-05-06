package crypto;

import org.junit.Assert;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

import model.crypto.AES;
import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCryptoCipher {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    private static final int MAXSIZE = 50;
    @Test
    public void testAES() {
        List<Integer> plaintextSize = new ArrayList<>();
        for (int i = 0; i < MAXSIZE; i++) {
            plaintextSize.add(i);
        }
        String key = new String("YELLOW SUBMARINEYELLOW SUBMARINE");
        CipherFactory cipherFactory = new CipherFactory();
        CryptoCipher aes = null;
        try {
            aes = cipherFactory.getCipher("AES", key.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        for (final Integer s: plaintextSize) {
            byte[] plaintext = new byte[s];
            Arrays.fill(plaintext, (byte) 'a');
            try {
                byte[] result = aes.decrypt(aes.encrypt(plaintext));
                Assert.assertArrayEquals(plaintext, result);
            } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        byte[] iv = new byte[AES.BLOCKSIZE];
        Arrays.fill(iv, (byte) '0');
        byte[] plaintext = new byte[AES.BLOCKSIZE];
        Arrays.fill(plaintext, (byte) 'a');
        AES cryptoAES = new AES(key.getBytes(), iv);
        byte[] result = cryptoAES.encrypt(plaintext);
        System.out.println(bytesToHex(result));
    }
}
