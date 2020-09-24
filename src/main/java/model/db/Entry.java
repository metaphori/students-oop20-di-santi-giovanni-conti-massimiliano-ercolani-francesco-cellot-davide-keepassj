package model.db;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "entry")
@XmlType(propOrder = {        "nameAccount",        "username",        "password",        "groupName",        "url",        "note"    })
public class Entry {

    private String nameAccount;
    private String username;
    private String password;
    private String groupName;
    private String url;
    private String note;

    //constructor used for test
    public Entry() {
        /*
        this.nameAccount = "prova";
        this.username = "account";
        this.password = "pass33--t";
        this.groupName = "Other";
        this.url = "www.ciao.it";
        this.note = "questa nota";
        */
    }

    public Entry(final String nameAccount, final String username, final String password, final Group group, final String url, final String note) {
        this.nameAccount = nameAccount;
        this.username = username;
        this.password = password;
        this.groupName = group.getName();
        this.url = url;
        this.note = note;
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
    @XmlElement(name = "Group")
    public String getGroupName() {
        return groupName;
    }

    /**
     * @param group selected of the entry
     */
    public void setGroupName(final Group group) {
        this.groupName = group.getName();
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
}
