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

    private static final int MAXSIZE = 50;

    private byte[] hexToBytes(final String hex) {
        try {
            return Hex.decodeHex(hex);
        } catch (DecoderException e) {
            System.out.println("Error decoding: " + e.toString());
        }
        return null;
    }
    @Test
    public void testAES() {
        final String plaintext = new String("This is the input");
        byte[] iv = new byte[AES.BLOCKSIZE];
        byte[] key = new byte[AES.BLOCKSIZE * 2];
        byte[] expected;
        // I used cyberchef and pycryptodome to confirm this output
        expected = hexToBytes("01d1c52d4aba05c503e38ec71ea11b7158f670041fc0ff7e549988924f2b7818");
        Arrays.fill(iv, (byte) 'b');
        Arrays.fill(key, (byte) 'a');
        CipherFactory cipherFactory = new CipherFactory();
        CryptoCipher cipher = cipherFactory.getCipher("AES", key);
        assertArrayEquals(expected, cipher.encrypt(plaintext.getBytes(), iv));
    }
}
