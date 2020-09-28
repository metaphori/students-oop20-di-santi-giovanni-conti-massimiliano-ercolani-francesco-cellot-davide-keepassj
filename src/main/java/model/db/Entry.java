package model.db;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "entry")
@XmlType(propOrder = {        "nameAccount",        "username",        "password",        "groupName",        "url",        "note", "stringNull"})
public class Entry {

    private String nameAccount;
    private String username;
    private String password;
    private String groupName;
    private String url;
    private String note;
    private String stringNull;

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
        setStringNull("*****");
    }

    public Entry(final String nameAccount, final String username, final String password, final Group group, final String url, final String note) {
        this.nameAccount = nameAccount;
        this.username = username;
        this.password = password;
        this.groupName = group.getName();
        this.url = url;
        this.note = note;
        setStringNull("*****");
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
     * @param group selected in String format
     */
    public void setGroupName(final String group) {
        this.groupName = group;
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

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((groupName == null) ? 0 : groupName.hashCode());
        result = prime * result + ((nameAccount == null) ? 0 : nameAccount.hashCode());
        result = prime * result + ((note == null) ? 0 : note.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entry other = (Entry) obj;
        if (groupName == null) {
            if (other.groupName != null) {
                return false;
            }
        } else if (!groupName.equals(other.groupName)) {
            return false;
        }
        if (nameAccount == null) {
            if (other.nameAccount != null) {
                return false;
            }
        } else if (!nameAccount.equals(other.nameAccount)) {
            return false;
        }
        if (note == null) {
            if (other.note != null) {
                return false;
            }
        } else if (!note.equals(other.note)) {
            return false;
        }
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (url == null) {
            if (other.url != null) {
                return false;
            }
        } else if (!url.equals(other.url)) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    public final String getStringNull() {
        return stringNull;
    }

    public final void setStringNull(final String stringNull) {
        this.stringNull = stringNull;
    }
}
