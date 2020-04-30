package repo;

import static org.junit.Assert.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import org.junit.Test;
import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;

public class TestCryptoCipher {

    @Test
    public void testAES() {
        String key = new String("YELLOW SUBMARINE");
        String plaintext = new String("This is a plaintext");
        CipherFactory cipherFactory = new CipherFactory();
        try {
            CryptoCipher aes = cipherFactory.getCipher("AES", key.getBytes());
            try {
                System.out.println(aes.decrypt(aes.encrypt(plaintext)));
                assertEquals(plaintext, aes.decrypt(aes.encrypt(plaintext)));
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
        fail("Not yet implemented");
    }

}
