package kdbx;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import model.kdbx.KDBHeader;
import model.kdbx.KDBReader;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

public class TestKDBReader {

    @Test
    public void testReadHeader() {
        final long transformRounds = 60000;
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("database.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        KDBReader db = new KDBReader(inputStream, credentials);
        KDBHeader header = db.getHeader();
        System.out.println(Hex.encodeHex(header.dataToBytes()));
        assertEquals(header.getCipher(), "AES");
        assertEquals(header.getTransformRounds(), transformRounds);
        // System.out.println(Hex.encodeHex(header.getTransformSeed()));
    }

}
