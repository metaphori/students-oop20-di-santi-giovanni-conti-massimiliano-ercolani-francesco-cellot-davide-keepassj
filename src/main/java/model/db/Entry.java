package model.db;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entry")
public class Entry {

    private String nameAccount;
    private String username;
    private String password;
    private String group;
    private String url;
    private String note;

    //constructor used for test
    public Entry() {
        this.nameAccount = "prova";
        this.username = "prova";
        this.password = "prova";
        this.group = "prova";
        this.url = "prova";
        this.note = "prova";
    }

    public Entry(final String nameAccount, final String username, final String password, final Group group, final String url, final String notes) {
        this.nameAccount = nameAccount;
        this.username = username;
        this.password = password;
        this.group = group.getName();
        this.url = url;
        this.note = notes;
    }

    /**
     * @return the nameAccount
     */
    @XmlElement(name = "nomeEntry")
    public String getNameAccount() {
        return nameAccount;
    }

    /**
     * @param nameAccount the nameAccount to set
     */
    public void setNameAccount(final String nameAccount) {
        this.nameAccount = nameAccount;
    }

    /**
     * @return username of the entry
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return String group of the entry
     */
    public String getGroup() {
        return group;
    }

    /**
     * @param group selected of the entry
     */
    public void setGroup(final Group group) {
        this.group = group.getName();
    }

    /**
     * @return the url where to use this entry
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note to set
     */
    public void setNote(final String note) {
        this.note = note;
    }

    /*
    //in prova e da spostare
    public final String toText() {
        //add quote ""
        final String nameA = "\"" + this.nameAccount + "\"";
        final String user = "\"" + this.username + "\"";
        final String pass = "\"" + this.password + "\"";
        final String webs = "\"" + this.url + "\"";
        final String notes = "\"" + this.note + "\"";

        return nameA + usern + passw + webs + notes;
    }
    */
}
