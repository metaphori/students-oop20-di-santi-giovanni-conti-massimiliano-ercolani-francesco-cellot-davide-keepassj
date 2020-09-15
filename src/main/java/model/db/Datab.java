package model.db;

import java.util.ArrayList;

public class Datab {

    private ArrayList<ElementDb> mydb;

    public Datab() {
        this.mydb = new ArrayList<>();
    }

    /**
     * Receive a new entry to insert.
     * @param entry
     * @return true if it's done
     */
    public final boolean addEntry(final ElementDb entry) {
        if (nameAlreadyExist(entry.getNameAccount())) {
            return false;
        }
        this.mydb.add(entry);
        return true;
    }

    /**
     * Receive a new entry to insert.
     * @param nameToDelete
     * @return true if it's done
     */
    public final boolean delEntry(final String nameToDelete) {
        ElementDb temp;
        if (getEntry(nameToDelete) == null) {
            return false;
        } else {
            temp = getEntry(nameToDelete);
        }

        this.mydb.remove(temp);
        return true;
    }

    /**
     * control if the array already contains an entry
     * with the same name.
     * @param nameAccount
     * @return true/false
     */
    public boolean nameAlreadyExist(final String nameAccount) {
        for (int i = 0; i < mydb.size(); i++) {
            if (this.mydb.get(i).getNameAccount() == nameAccount) {
                return true;
            }
        }
        return false;
    }

    /**
     * from the unique nameAccount return the entire Entry.
     * @param nameAccount
     * @return the entry or null if not found
     */
    public ElementDb getEntry(final String nameAccount) {
        for (int i = 0; i < mydb.size(); i++) {
            if (this.mydb.get(i).getNameAccount() == nameAccount) {
                return mydb.get(i);
            }
        }
        return null;
    }

    /**
     * Return the statement of the arraylist.
     * @return true/false
     */
    public Boolean isEmpty() {
        if (mydb.isEmpty()) {
            return true;
        }
        return false;
    }
}
