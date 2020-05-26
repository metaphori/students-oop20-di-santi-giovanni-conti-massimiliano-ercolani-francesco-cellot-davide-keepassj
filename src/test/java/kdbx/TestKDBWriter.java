package kdbx;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.kdbx.KDBHeader;
import model.kdbx.KDBReader;
import model.kdbx.KDBWriter;

public class TestKDBWriter {

    @Test
    public void test() {
        final long transformRounds = 60000;
        final InputStream inputStream = ClassLoader.getSystemResourceAsStream("database.kdbx");
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        KDBReader db = new KDBReader(inputStream, credentials);
        KDBHeader header = db.getHeader();
        // header.dataToBytes();
        assertEquals(header.getCipher(), "AES");
        assertEquals(header.getTransformRounds(), transformRounds);
        OutputStream out = null;
        try {
            out = new FileOutputStream("write.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        KDBWriter dbWrite = new KDBWriter(header, out);
        dbWrite.write();
    }

}
