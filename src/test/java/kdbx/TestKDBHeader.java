package kdbx;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

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
        assertEquals(32768, header.getKDFMemory());
        kdfType = "PBKDF2";
        header.setKDF(kdfType);
        assertEquals(10000, header.getTransformRounds());
        assertEquals(kdfType, header.getKDF());
        assertEquals(false, header.isKDFTweakable(kdfType));
    }

    @SuppressWarnings("MagicNumber")
    @Test
    public void testKDFParameters() {
        final KDBHeader header = new KDBHeader();
        header.setKDFParallelism(4);
        header.setKDFMemory(6500);
        System.out.println(Hex.encodeHex(header.writeHeader()));
    }

    @Test
    public void testGetter() {
        final KDBHeader header = new KDBHeader();
        header.getCipherDescriptions().entrySet().forEach(s -> System.out.println(s.getKey() + " " + s.getValue()));
        header.getKDFDescriptions().entrySet().forEach(s -> System.out.println(s.getKey() + " " + s.getValue()));
    }
}
