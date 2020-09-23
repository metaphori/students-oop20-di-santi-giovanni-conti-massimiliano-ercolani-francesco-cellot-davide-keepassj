package model.export;

import model.db.Database;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


public final class ConvertXml {

    private ConvertXml() {
        return;
    }

    /*
     * that.
     * @param Database to convert
     * @return String that contain XML row to write
     */
    public static String toXml(final Database db) {
        // create JAXB context
        try {
            JAXBContext context = JAXBContext.newInstance(Database.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(db, System.out);
            m.marshal(db, new File("database-jaxb.xml"));
            //StringWriter app = new StringWriter();
            //m.marshal(db, app);
            //return app.toString();
            return "";
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Database fromXml(final String xmlToOpen) {
        try {
            JAXBContext context = JAXBContext.newInstance(Database.class);
            Unmarshaller un = context.createUnmarshaller();
            Database db = (Database) un.unmarshal(new File("database-jaxb.xml"));
            return db;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    Address address = customer.getAddress();
    System.out.println(address.getStreet());

    Marshaller marshaller = jc.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.marshal(customer, System.out);
     */
    /*
    JAXBContext jc = JAXBContext.newInstance(Database.class);

    Unmarshaller unmarshaller = jc.createUnmarshaller();
    File xml = new File("input.xml");
    Customer customer = (Customer) unmarshaller.unmarshal(xml);

    Address address = customer.getAddress();
    System.out.println(address.getStreet());

    Marshaller marshaller = jc.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    marshaller.marshal(customer, System.out);


    // Step 2 - Convert the Domain Model to XML
    JAXBContext jaxbContext = JAXBContext.newInstance(Database.class);

    Marshaller marshaller = jaxbContext.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

    StringWriter xmlWriter = new StringWriter();
    marshaller.marshal(db, xmlWriter);
    System.out.println(xmlWriter.toString());

    // Step 3 - Convert XML back to Domain Model
    Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
    StringReader xmlReader = new StringReader(xmlWriter.toString());
    Database outCustomer = (Datab) unmarshaller.unmarshal(xmlReader);
    System.out.println(outCustomer);
     */
}
