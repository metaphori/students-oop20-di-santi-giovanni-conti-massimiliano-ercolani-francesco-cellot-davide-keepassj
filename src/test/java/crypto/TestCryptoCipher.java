package crypto;

import org.junit.Assert;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.Test;
import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCryptoCipher {

    private static final int MAXSIZE = 50;
    @Test
    public void testAES() {
        List<Integer> plaintextSize = new ArrayList<>();
        for (int i = 0; i < MAXSIZE; i++) {
            plaintextSize.add(i);
        }
        String key = new String("YELLOW SUBMARINEYELLOW SUBMARINE");
        for (final Integer s: plaintextSize) {
            byte[] plaintext = new byte[s];
            Arrays.fill(plaintext, (byte) 'a');
            CipherFactory cipherFactory = new CipherFactory();
            try {
                CryptoCipher aes = cipherFactory.getCipher("AES", key.getBytes());
                try {
                    byte[] result = aes.decrypt(aes.encrypt(plaintext));
                    Assert.assertArrayEquals(plaintext, result);
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

}
