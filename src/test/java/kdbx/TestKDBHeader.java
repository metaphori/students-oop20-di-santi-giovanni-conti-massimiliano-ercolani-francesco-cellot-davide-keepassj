package kdbx;

import static org.junit.Assert.*;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import model.kdbx.KDBHeader;

public class TestKDBHeader {

    @Test
    public void test() {
        KDBHeader header = new KDBHeader();
        System.out.println(Hex.encodeHex(header.writeHeader()));
    }

}
