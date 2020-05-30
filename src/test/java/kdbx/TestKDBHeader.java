package kdbx;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import model.kdbx.KDBHeader;

public class TestKDBHeader {

    @Test
    public void testCipher() {
        String cipherType = "ChaCha20Poly1305";
        KDBHeader header = new KDBHeader();
        header.setCipher(cipherType);
        assertEquals(cipherType, header.getCipher());
        cipherType = "AESGCM";
        header.setCipher(cipherType);
        assertEquals(cipherType, header.getCipher());
    }

    @Test
    public void testKDF() {
        String kdfType = "Argon2";
        KDBHeader header = new KDBHeader();
        header.setKDF(kdfType);
        assertEquals(kdfType, header.getKDF());
        kdfType = "PBKDF2";
        header.setKDF(kdfType);
        assertEquals(kdfType, header.getKDF());
    }

    @Test
    public void testKDFParameters() {
        KDBHeader header = new KDBHeader();
        header.setKDFParallelism(4);
        header.setKDFMemory(6500);
        System.out.println(Hex.encodeHex(header.writeHeader()));
    }

}
