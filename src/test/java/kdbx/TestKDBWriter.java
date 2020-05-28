package kdbx;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.kdbx.KDBHeader;
import model.kdbx.KDBWriter;

public class TestKDBWriter {

    @Test
    public void test() {
        final List<byte[]> credentials = Arrays.asList("ciao".getBytes());
        OutputStream out = null;
        try {
            out = new FileOutputStream("write.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        KDBHeader header = new KDBHeader();
        KDBWriter dbWrite = new KDBWriter(header, out, credentials);
        byte[] plaintext = "This is a test".getBytes();
        dbWrite.write(plaintext);
    }

}
