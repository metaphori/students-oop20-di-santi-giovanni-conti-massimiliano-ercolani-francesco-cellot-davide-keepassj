package model.export;
import model.db.Database;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class ConvertToXML {

    /*
     * that.
     * @param Database to convert
     * @return String that contain XML row to write
     */
    public static String getXml(final Database db) {
        JAXBContext jc = JAXBContext.newInstance(CustomerImpl.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        File xml = new File("input.xml");
        Customer customer = (Customer) unmarshaller.unmarshal(xml);

        Address address = customer.getAddress();
        System.out.println(address.getStreet());

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(customer, System.out);
        return "";
    }
/*
        // Step 2 - Convert the Domain Model to XML
        JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
 
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
        StringWriter xmlWriter = new StringWriter();
        marshaller.marshal(customer, xmlWriter);
        System.out.println(xmlWriter.toString());

        // Step 3 - Convert XML back to Domain Model
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader xmlReader = new StringReader(xmlWriter.toString());
        Customer outCustomer = (Customer) unmarshaller.unmarshal(xmlReader);
        System.out.println(outCustomer);
*/
}
