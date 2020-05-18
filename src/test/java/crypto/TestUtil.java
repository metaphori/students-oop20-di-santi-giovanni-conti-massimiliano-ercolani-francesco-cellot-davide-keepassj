package crypto;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import javax.crypto.BadPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import model.crypto.Util;

public class TestUtil {

    @Test
    public void testSHA256() {
        String expected = new String("61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4");
        String input = new String("aaaa");
        String res = Hex.encodeHexString(Util.sha256(input.getBytes()));
        assertEquals(expected, res);
    }

    @Test
    public void testPKCS7Padding() {
        final int blocksize = 16;
        String expected = new String("616161610c0c0c0c0c0c0c0c0c0c0c0c");
        String input = new String("aaaa");
        String res = Hex.encodeHexString(Util.pad(input.getBytes(), blocksize));
        assertEquals(expected, res);
        try {
            res = new String(Util.unpad(Hex.decodeHex(res)));
            assertEquals(input, res);
        } catch (BadPaddingException | DecoderException e) {
            System.out.println("Bad Padding: " + e.toString());
        }
    }

    @Test
    public void testHmac() {
        byte[] expected = null;
        try {
            expected = Hex.decodeHex("f7bc83f430538424b13298e6aa6fb143ef4d59a14946175997479dbc2d1a3cd8");
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        byte[] message = "The quick brown fox jumps over the lazy dog".getBytes();
        byte[] key = "key".getBytes();
        // values taken from wikipedia
        assertArrayEquals(expected, Util.hmac256(key, message));
    }
}
