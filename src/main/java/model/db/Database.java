package model.db;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import model.export.ConvertToXML;
import model.kdbx.KDB;

public class Database {

    private ArrayList<Entry> entryList;
    private ArrayList<String> categoryList;
    private KDB cryptoDb;
    private String plaintext;

    public Database(final KDB cryptoDb) throws FileNotFoundException {
        this.entryList = new ArrayList<>();
        this.categoryList = new ArrayList<>();
        this.cryptoDb = cryptoDb;
        plaintext = updateTextForXml();
        cryptoDb.write(plaintext.getBytes());
    }

    private String updateTextForXml() {
        String app = ConvertToXML.getXml(this);
        return (app != null) ? app : "";
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
     * @param nameToDelete
     * @return true if it's done
     */
    public final boolean delEntry(final String nameToDelete) {
        Entry temp;
        if (getEntry(nameToDelete) == null) {
            return false;
        } else {
            temp = getEntry(nameToDelete);
        }

        this.entryList.remove(temp);
        return true;
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
        return (entryList.stream().filter(e -> e.getNameAccount() == nameAccount).count() != 0) ? true : false;
    }

    /**
     * from the unique nameAccount return the entire Entry.
     * @param nameAccount
     * @return the entry or null if not found
     */
    public Entry getEntry(final String nameAccount) {
        for (int i = 0; i < entryList.size(); i++) {
            if (this.entryList.get(i).getNameAccount() == nameAccount) {
                return entryList.get(i);
            }
        }
        return null;
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
     * @param category
     * @return true if it's done, false if already exist of something wrong
     */
    public final boolean addCategory(final String category) {
        if (categoryList.contains(category)) {
            return false;
        }
        this.categoryList.add(category);
        return true;
    }

    /**
     * Receive a new entry to insert.
     * @param category
     * @return true if it's done, false if don't contain it of something wrong
     */
    public final boolean delCategory(final String category) {
        if (!categoryList.contains(category)) {
            return false;
        }
        this.categoryList.remove(category);
        return true;
    }
}
