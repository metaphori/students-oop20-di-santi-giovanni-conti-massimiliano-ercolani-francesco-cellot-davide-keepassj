package crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.primitives.Bytes;

import model.crypto.AES;
import model.crypto.CipherFactory;
import model.crypto.CryptoCipher;

import java.util.Arrays;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class TestCryptoCipher {

    @Test
    public void testAES() {
        try {
            // Values taken from https://tools.ietf.org/html/draft-mcgrew-aead-aes-cbc-hmac-sha2-05, page20
            final byte[] k = Hex.decodeHex("000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f");
            final byte[] p = Hex.decodeHex("41206369706865722073797374656d206d757374206e6f7420626520726571756972656420746f206265207365637265742c20616e64206974206d7573742062652061626c6520746f2066616c6c20696e746f207468652068616e6473206f662074686520656e656d7920776974686f757420696e636f6e76656e69656e6365");
            final byte[] iv = Hex.decodeHex("1af38c2dc2b96ffdd86694092341bc04");
            final byte[] a = Hex.decodeHex("546865207365636f6e64207072696e6369706c65206f662041756775737465204b6572636b686f666673");
            final byte[] t = Hex.decodeHex("4dd3b4c088a7f45c216839645b2012bf2e6269a8c56a816dbc1b267761955bc5");
            final int indexTag = 144;
            CryptoCipher aes = CipherFactory.create("AES");
            aes.setKey(k);
            aes.updateAssociatedData(a);
            final byte[] c = aes.encrypt(p, iv);
            assertTrue(Bytes.indexOf(c, t) == indexTag);
            assertArrayEquals(p, aes.decrypt(c, iv));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAESGCM() {
        final byte[] plaintext = "aaaaaaaaaaaaaaaa".getBytes();
        final CryptoCipher aesGcm = CipherFactory.create("AESGCM");
        final byte[] iv = new byte[aesGcm.getIVSize()];
        final byte[] key = new byte[aesGcm.getKeySize()];
        Arrays.fill(iv, (byte) 'b');
        Arrays.fill(key, (byte) 'a');
        aesGcm.setKey(key);
        assertArrayEquals(plaintext, aesGcm.decrypt(aesGcm.encrypt(plaintext, iv), iv));
        // final byte[] ciphertext = aesGcm.encrypt(plaintext, iv);
        // System.out.println(new String(aesGcm.decrypt(ciphertext, iv)));
    }

    @Test
    public void testChaCha20Poly1305() {
        final byte[] plaintext = "aaaaaaaaaaaaaaaa".getBytes();
        final CryptoCipher chacha20poly1305 = CipherFactory.create("ChaCha20Poly1305");
        final byte[] iv = new byte[chacha20poly1305.getIVSize()];
        final byte[] key = new byte[chacha20poly1305.getKeySize()];
        Arrays.fill(iv, (byte) 'b');
        Arrays.fill(key, (byte) 'a');
        chacha20poly1305.setKey(key);
        assertArrayEquals(plaintext, chacha20poly1305.decrypt(chacha20poly1305.encrypt(plaintext, iv), iv));
    }

    /*
    @Test(expected = javax.crypto.AEADBadTagException.class)
    public void testChaCha20Poly1305Auth() {
        final byte[] plaintext = "aaaaaaaaaaaaaaaa".getBytes();
        final CryptoCipher chacha20poly1305 = CipherFactory.create("ChaCha20Poly1305");
        final byte[] iv = new byte[chacha20poly1305.getIVSize()];
        final byte[] key = new byte[chacha20poly1305.getKeySize()];
        Arrays.fill(iv, (byte) 'b');
        Arrays.fill(key, (byte) 'a');
        chacha20poly1305.setKey(key);
        final byte[] ciphertext = chacha20poly1305.encrypt(plaintext, iv);
        ciphertext[10] += 1;
        chacha20poly1305.decrypt(ciphertext, iv);
    }
    */
}
