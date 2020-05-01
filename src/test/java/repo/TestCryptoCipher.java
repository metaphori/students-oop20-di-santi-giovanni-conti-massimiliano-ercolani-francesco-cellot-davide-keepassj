package repo;

import org.junit.Assert;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.Test;
import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;
import java.util.Arrays;

public class TestCryptoCipher {

    @Test
    public void testAES() {
        String key = new String("YELLOW SUBMARINEYELLOW SUBMARINE");
        byte[] plaintext = new byte[16];
        Arrays.fill(plaintext, (byte) 'a');

        CipherFactory cipherFactory = new CipherFactory();
        try {
            CryptoCipher aes = cipherFactory.getCipher("AES", key.getBytes());
            try {
                byte[] result = aes.decrypt(aes.encrypt(new String(plaintext)));
                Assert.assertArrayEquals(plaintext, result);
                System.out.println("AES is working");
            } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
