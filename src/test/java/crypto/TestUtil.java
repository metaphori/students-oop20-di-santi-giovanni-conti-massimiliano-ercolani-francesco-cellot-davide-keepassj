package crypto;

import static org.junit.Assert.assertEquals;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import model.crypto.Util;

public class TestUtil {

    @Test
    public void testSha256() {
        String expected = new String("61be55a8e2f6b4e172338bddf184d6dbee29c98853e0a0485ecee7f27b9af0b4");
        String input = new String("aaaa");
        String res = Hex.encodeHexString(Util.sha256(input.getBytes()));
        assertEquals(expected, res);
    }

}
