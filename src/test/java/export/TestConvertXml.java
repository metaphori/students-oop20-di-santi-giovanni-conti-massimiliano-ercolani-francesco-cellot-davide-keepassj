package export;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import model.db.Database;
import model.db.Entry;
import model.db.Group;
import model.export.ConvertXml;

public class TestConvertXml {

    @org.junit.Test
    public void testXml() throws JAXBException, IOException {
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

        /*String nomeFile = "testDatabase-xml.xml";
        try {
            FileWriter myWriter = new FileWriter(nomeFile);
            myWriter.write(app);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Database recreateDb = ConvertXml.fromXml(app);
        assertNotNull(recreateDb);
        //System.out.println(app);

        assertEquals(myDb.getAllEntry().size(), recreateDb.getAllEntry().size());
    }
}
