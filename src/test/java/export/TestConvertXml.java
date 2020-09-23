package export;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.bind.JAXBException;

import model.db.*;
import model.export.ConvertXml;

public class TestConvertXml {

    @org.junit.Test
    public void testXml() throws JAXBException {
        //create of Database to parse
        Database myDb = new Database();
        Entry one = new Entry();
        one.setNameAccount("one");
        Entry two = new Entry();
        two.setNameAccount("two");
        myDb.addEntry(one);
        myDb.addEntry(two);
        myDb.addGroup(new Group("prova"));

        //active test
        String app = ConvertXml.toXml(myDb);
        assertNotNull(app);
        //File file = new File("database-jaxb.xml");
        Database recreateDb = ConvertXml.fromXml(app);
        assertNotNull(recreateDb);
        System.out.println(app);

        assertEquals(myDb.getAllEntry().size(), recreateDb.getAllEntry().size());
    }
}
