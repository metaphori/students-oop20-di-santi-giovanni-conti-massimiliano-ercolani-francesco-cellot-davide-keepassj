package model.db;

import java.util.ArrayList;

public class Datab {

    private ArrayList<ElementDb> mydb;

    public Datab() {
        this.mydb = new ArrayList<>();
    }

    public final boolean addEntry(final ElementDb entry) {
        if (nameAlreadyExist(entry.getNameAccount())) {
            return false;
        }
        this.mydb.add(entry);
        return true;
    }

    /**
     * control if the array already contains an entry
     * with the same name.
     * @param nameAccount
     * @return result
     */
    public boolean nameAlreadyExist(final String nameAccount) {
        for (int i = 0; i < mydb.size(); i++) {
            if (this.mydb.get(i).getNameAccount() == nameAccount) {
                return true;
            }
        }
        return false;
    }

}
