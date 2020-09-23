package model.db;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import model.export.ConvertXml;
import model.kdbx.KDB;

@XmlRootElement(namespace = "model.db")
public class Database {

    @XmlElementWrapper(name = "entryList")
    @XmlElement(name = "entry")
    private ArrayList<Entry> entryList;
    @XmlElementWrapper(name = "groupList")
    @XmlElement(name = "group")
    private ArrayList<Group> groupList;
    //@XmlElementWrapper(name = "kdb")
    private KDB cryptoDb;

    public Database() {
        this.entryList = new ArrayList<>();
        this.groupList = new ArrayList<>();
    }

    public Database(final KDB cryptoDb) throws JAXBException {
        this.entryList = new ArrayList<>();
        this.groupList = new ArrayList<>();
        this.cryptoDb = cryptoDb;
        writeXml();
    }

    /*
     * Constructor called when open a Database already exist.
     */
    public Database(final Database db) {
        this.entryList = db.entryList;
        this.groupList = db.groupList;
        //this.cryptoDb = db.cryptoDb;
    }

    /*
     * Convert the Database to an Xml String.
     */
    public final String getXml() throws JAXBException {
        return ConvertXml.toXml(this);
    }

    /*
     * Write the Xml String to file trough encryption of KDB object.
     */
    private void writeXml() throws JAXBException {
        try {
        cryptoDb.write(getXml().getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //ConvertXml.fromXml(cryptoDb.read().toString());
    }

    /**
     * Receive a new entry to insert.
     * @param entry
     * @return true if it's done
     */
    public final boolean addEntry(final Entry entry) {
        if (nameAlreadyExist(entry.getNameAccount())) {
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
        return this.entryList.removeIf(e -> e.getNameAccount() == entry.getNameAccount());
    }

    /*
     * get all the Entry entered.
     * return ArrayList of Entry
     */
    public final ArrayList<Entry> getAllEntry() {
        return this.entryList;
    }

    /**
     * control if the array already contains an entry
     * with the same name.
     * @param nameAccount
     * @return true/false
     */
    public boolean nameAlreadyExist(final String nameAccount) {
        /*
        for (int i = 0; i < entryList.size(); i++) {
            if (this.entryList.get(i).getNameAccount() == nameAccount) {
                return true;
            }
        }
         */
        return (entryList.stream()
                .filter(e -> e.getNameAccount() == nameAccount)
                .count() == 0) ? false : true;
    }

    /**
     * from the unique nameAccount return the entire Entry.
     * @param nameAccount
     * @return the entry or null if not found
     */
    public Entry getEntry(final String nameAccount) {
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
     * Return the statement of entryList.
     * @return true if it's empty, false if not
     */
    public Boolean isEmpty() {
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
    public final boolean delGroup(final Group group) {
        if (groupList.contains(group)) {
            if (entryList.stream().filter(e -> e.getGroup() == group.getName()).count() == 0) {
                this.groupList.remove(group);
                return true;
            }
        }
        return false;
    }

    /*
     * Return a list on Entry all under the same group.
     * @param group
     * @return ArrayList<Entry>
     */
    public final ArrayList<Entry> getAllEntryOfSpecifiedGroup(final Group group) {
        ArrayList<Entry> app = new ArrayList<Entry>(
                                this.entryList.stream()
                                .filter(e -> e.getGroup() == group.getName())
                                .collect(Collectors.toList()));
                                //.collect(Collectors.toCollection(ArrayList::new));
        return app;
    }
}
