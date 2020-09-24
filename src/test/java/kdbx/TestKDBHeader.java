package kdbx;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import model.crypto.KDFBadParameter;
import model.kdbx.KDBHeader;

public class TestKDBHeader {

    @Test
    public void testCipher() {
        String cipherType = "ChaCha20Poly1305";
        final KDBHeader header = new KDBHeader();
        header.setCipher(cipherType);
        assertEquals(cipherType, header.getCipher());
        cipherType = "AESGCM";
        header.setCipher(cipherType);
        assertEquals(cipherType, header.getCipher());
    }

    @Test
    public void testKDF() {
        final int memory1 = 32_768;
        String kdfType = "Argon2";
        final KDBHeader header = new KDBHeader();
        header.setKDF(kdfType);
        assertEquals(true, header.isKDFTweakable("Argon2"));
        assertEquals(kdfType, header.getKDF());
        final int rounds = 60;
        assertEquals(rounds, header.getKDFRounds(kdfType));
        assertEquals(rounds, header.getTransformRounds());
        header.setTransformRounds(rounds + 2);
        assertEquals(rounds + 2, header.getTransformRounds());
        assertEquals(2, header.getKDFParallelism());
        assertEquals(memory1, header.getKDFMemory());
        kdfType = "PBKDF2";
        header.setKDF(kdfType);
        final int memory2 = 10_000;
        assertEquals(memory2, header.getTransformRounds());
        assertEquals(kdfType, header.getKDF());
        assertEquals(false, header.isKDFTweakable(kdfType));
    }

    @SuppressWarnings("MagicNumber")
    @Test
    public void testKDFParameters() throws KDFBadParameter {
        final KDBHeader header = new KDBHeader();
        header.setKDFParallelism(4);
        header.setKDFMemory(32_768 * 2);
        System.out.println(Hex.encodeHex(header.writeHeader()));
    }

    @Test
    public void testGetter() {
        final KDBHeader header = new KDBHeader();
        header.getCipherDescriptions().entrySet().forEach(s -> System.out.println(s.getKey() + " " + s.getValue()));
        header.getKDFDescriptions().entrySet().forEach(s -> System.out.println(s.getKey() + " " + s.getValue()));
    }

    @Test
    public void testComments() {
        final KDBHeader header = new KDBHeader();
        header.setComment("ciao".getBytes());
        header.setPublicCustomData("testino".getBytes());
    }
}
