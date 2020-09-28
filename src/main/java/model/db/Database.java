package model.db;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.AEADBadTagException;
import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import model.export.ConvertXml;
import model.kdbx.KDB;

@XmlRootElement(namespace = "model.db")
@XmlType(propOrder = {"nomeDatabase", "groupList", "entryList"})
public class Database {

    private String nomeDatabase;
    @XmlElementWrapper(name = "entryList")
    @XmlElement(name = "entry")
    private List<Entry> entryList;
    @XmlElementWrapper(name = "groupList")
    @XmlElement(name = "group")
    private List<Group> groupList;
    //@XmlElementWrapper(name = "kdb")
    private KDB cryptoDb;

    public Database() {
        this.entryList = new ArrayList<>();
        this.groupList = new ArrayList<>();
    }

    public Database(final KDB cryptoDb) {
        this.entryList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.cryptoDb = cryptoDb;
        //writeXml();
    }

    /**
     * Convert the Database to an Xml String.
     */
    public final String getXml() throws JAXBException {
        return ConvertXml.toXml(this);
    }

    /*
     * Write the Xml String to file trough encryption of KDB object.
     */
    public final void writeXml() throws JAXBException {
        try {
            cryptoDb.write(getXml().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //ConvertXml.fromXml(cryptoDb.read().toString());
    }

    /**
     * use cryptoDB to encrypt the Xml.
     * @return String xml
     */
    public final String readCryptoFile() throws AEADBadTagException {
        try {
            String app = "";
            try {
                app = new String(cryptoDb.read(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                System.out.print("error in StandardCharsets that's not utf-8");
                e.printStackTrace();
            }
            return app;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error in readCryptoFile");
            return null;
        }
    }

    /**
     * fill the Database with the xml file through cryptoDB encryption.
     * @throws AEADBadTagException
     */
    public final void readXml() throws AEADBadTagException {
        final Database app = ConvertXml.fromXml(readCryptoFile());
        if (app == null) {
            return;
        }
        this.entryList = app.entryList;
        this.groupList = app.groupList;
        this.nomeDatabase = app.nomeDatabase;
    }

    /**
     * Receive a new entry to insert.
     * @param entry
     * @return true if it's done
     */
    public final boolean addEntry(final Entry entry) {
        if (entryAlreadyExist(entry)) {
            return false;
        }
        this.entryList.add(entry);
        return true;
    }

    /**
     * Receive a new entry to insert.
     * @param entry
     * @return true if it's done
     */
    public final boolean deleteEntry(final Entry entry) {
        /*
        Entry temp;
        if (getEntry(nameToDelete) == null) {
            return false;
        } else {
            temp = getEntry(nameToDelete);
        }
        this.entryList.remove(temp);
         */
        return this.entryList.removeIf(e -> e.equals(entry));
    }

    /*
    public final Boolean editEntry(final Entry entryToEdit, final ArrayList<String> gg) {
        Entry temp = entryToEdit;
        temp.se
        this.entryList.set(this.entryList.indexOf(entryToEdit), entryToEdit);
        return true;
    }
     */

    /**
     * get all the Entry entered.
     * @return ArrayList of Entry
     */
    public final List<Entry> getAllEntry() {
        return this.entryList;
    }

    /**
     * get all the Group entered.
     * @return ArrayList of Group
     */
    public final List<Group> getAllGroup() {
        return this.groupList;
    }

    /**
     * control if the array already contains an entry
     * with the same name.
     * @param entry
     * @return true/false
     */
    public final boolean entryAlreadyExist(final Entry entry) {
        /*
        for (int i = 0; i < entryList.size(); i++) {
            if (this.entryList.get(i).getNameAccount() == nameAccount) {
                return true;
            }
        }
         */
        return (entryList.stream()
                .filter(e -> e.equals(entry))
                .count() == 0) ? false : true;
    }

    /**
     * from the unique nameAccount return the entire Entry.
     * @param nameAccount
     * @return the entry or null if not found
     */
    public final Entry getEntry(final String nameAccount) {
        /*
        for (int i = 0; i < entryList.size(); i++) { 
            if (this.entryList.get(i).getNameAccount() == nameAccount) {
                return entryList.get(i); 
            } 
        }*/
        //return null;

        return this.entryList.stream().filter(e -> e.getNameAccount() == nameAccount).findFirst().get();
    }

    /**
     * from the unique nameAccount return the entire Entry.
     * @param groupName
     * @return the group or null if not found
     */
    public final Group getGroup(final String groupName) {

        return this.groupList.stream().filter(e -> e.getName() == groupName).findFirst().get();
    }

    /**
     * Return the statement of entryList.
     * @return true if it's empty, false if not
     */
    public final Boolean isEmpty() {
        if (entryList.isEmpty()) {
            return true;
        }
        return false;
    }



    /**
     * Receive a new entry to insert.
     * @param group
     * @return true if it's done, false if already exist of something wrong
     */
    public final boolean addGroup(final Group group) {
        if (groupList.contains(group)) {
            return false;
        }
        this.groupList.add(group);
        return true;
    }

    /**
     * Receive a new entry to insert.
     * @param group
     * @return true if it's done, false if don't contain it of something wrong
     */
    public final boolean deleteGroup(final Group group) {
        if (groupList.contains(group)) {
            if (entryList.stream().filter(e -> e.getGroupName() == group.getName()).count() == 0) {
                this.groupList.remove(group);
                return true;
            }
        }
        return false;
    }

    /**
     * Return a list on Entry all under the same group.
     * @param group
     * @return ArrayList<Entry>
     */
    public final List<Entry> getAllEntryOfSpecifiedGroup(final Group group) {
        return this.entryList.stream()
                .filter(e -> e.getGroupName() == group.getName())
                .collect(Collectors.toList());
        //.collect(Collectors.toCollection(ArrayList::new));
        //return app;
    }

    /**
     * return the name of Database.
     * @return nomeDatabase
     */
    public final String getNomeDatabase() {
        return nomeDatabase;
    }

    /**
     * set name of Database.
     * @param nomeDatabase
     */
    public final void setNomeDatabase(final String nomeDatabase) {
        this.nomeDatabase = nomeDatabase;
    }
}
