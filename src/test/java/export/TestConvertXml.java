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
        final Database myDb = new Database();
        final Group group = new Group("Other");
        myDb.addEntry(new Entry("one", "", "", group, "", ""));
        myDb.addEntry(new Entry("two", "", "", group, "", ""));
        myDb.addGroup(group);

        //active test
        final String app = ConvertXml.toXml(myDb);
        assertNotNull(app);

        /*String nomeFile = "testDatabase-xml.xml";
        try {
            FileWriter myWriter = new FileWriter(nomeFile);
            myWriter.write(app);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        final Database recreateDb = ConvertXml.fromXml(app);
        assertNotNull(recreateDb);
        //System.out.println(app);

        assertEquals(2, myDb.getAllEntry().size());
        assertEquals(2, recreateDb.getAllEntry().size());
        //assertEquals(myDb.getAllEntry().size(), recreateDb.getAllEntry().size());
    }
}
