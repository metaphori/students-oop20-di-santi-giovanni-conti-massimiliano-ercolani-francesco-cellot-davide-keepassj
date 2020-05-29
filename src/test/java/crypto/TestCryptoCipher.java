package crypto;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import model.crypto.AES;
import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;

import java.util.Arrays;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class TestCryptoCipher {

    @Test
    public void testAES() {
        final String plaintext = new String("This is the input");
        byte[] iv = new byte[AES.BLOCK_SIZE];
        byte[] key = new byte[AES.BLOCK_SIZE * 2];
        byte[] expected = null;
        // I used cyberchef and pycryptodome to confirm this output
        try {
            expected = Hex.decodeHex("01d1c52d4aba05c503e38ec71ea11b7158f670041fc0ff7e549988924f2b7818");
        } catch (DecoderException e) {
            System.out.println("Error decoding hex: " + e.toString());
        }
        Arrays.fill(iv, (byte) 'b');
        Arrays.fill(key, (byte) 'a');
        CryptoCipher cipher = CipherFactory.create("AES");
        cipher.setKey(key);
        assertArrayEquals(expected, cipher.encrypt(plaintext.getBytes(), iv));
    }

    @Test
    public void testAESGCM() {
        final byte[] plaintext = "aaaaaaaaaaaaaaaa".getBytes();
        CryptoCipher aesGcm = CipherFactory.create("AESGCM");
        final byte[] iv = new byte[aesGcm.getIVSize()];
        final byte[] key = new byte[aesGcm.getKeySize()];
        Arrays.fill(iv, (byte) 'b');
        Arrays.fill(key, (byte) 'a');
        aesGcm.setKey(key);
        final byte[] ciphertext = aesGcm.encrypt(plaintext, iv);
        // Tag mismatch
        // ciphertext[10] = 5;
        System.out.println(new String(aesGcm.decrypt(ciphertext, iv)));
    }
}
