package kdbx;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import model.kdbx.KDB4File;
import org.junit.Test;

public class KDB4Reader {

    @Test
    public void test() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        final InputStream inputStream = classLoader.getSystemResourceAsStream("database.kdbx");
        KDB4File db = new KDB4File(inputStream);
        db.readHeader();
    }

}
