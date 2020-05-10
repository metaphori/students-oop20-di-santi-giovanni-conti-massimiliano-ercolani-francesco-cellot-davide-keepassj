package kdbx;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import model.kdbx.KDB4File;
import org.junit.Test;

public class KDB4Reader {

    @Test
    public void test() {
        File file = new File(getClass().getResource("kdbx.database.kdbx").getFile());
        FileInputStream fileInputStream = null;
        KDB4File db = null;
        try {
            fileInputStream = new FileInputStream(file);
            db = new KDB4File(fileInputStream);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

}
