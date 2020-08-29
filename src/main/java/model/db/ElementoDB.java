package model.db;

public class ElementoDB {

    private String nameAccount;
    private String username;
    private String password;
    private String website;
    private String note;

    public ElementoDB() { }

    public ElementoDB(final String nameAcc, final String user, final String passw, final String webs, final String notes) {
        this.nameAccount = nameAcc;
        this.username = user;
        this.password = passw;
        this.website = webs;
        this.note = notes;
    }

    /**
     * @return the nameAccount
     */
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
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
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(final String website) {
        this.website = website;
    }

    /**
     * @return the note
     */
    public String getNote() {
        return note;
    }

    /**
     * @param note the note to set
     */
    public void setNote(final String note) {
        this.note = note;
    }

    //in prova e da spostare
    public final String toText() {
        //add quote ""
        String nameA = "\"" + this.nameAccount + "\"";
        String usern = "\"" + this.username + "\"";
        String passw = "\"" + this.password + "\"";
        String webs = "\"" + this.website + "\"";
        String notes = "\"" + this.note + "\"";

        return nameA + usern + passw + webs + notes;
    }
}
