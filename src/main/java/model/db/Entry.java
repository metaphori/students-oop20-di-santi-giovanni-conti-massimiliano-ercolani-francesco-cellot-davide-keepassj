package model.db;

public class Entry {

    private String nameAccount;
    private String username;
    private String password;
    private String category;
    private String url;
    private String note;

    public Entry() { }

    public Entry(final String nameAccount, final String username, final String password, final String category, final String url, final String notes) {
        this.nameAccount = nameAccount;
        this.username = username;
        this.password = password;
        this.setCategory(category);
        this.url = url;
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
     * @return the category of the entry
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category selected of the entry
     */
    public void setCategory(final String category) {
        this.category = category;
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

    //in prova e da spostare
    public final String toText() {
        //add quote ""
        String nameA = "\"" + this.nameAccount + "\"";
        String usern = "\"" + this.username + "\"";
        String passw = "\"" + this.password + "\"";
        String webs = "\"" + this.url + "\"";
        String notes = "\"" + this.note + "\"";

        return nameA + usern + passw + webs + notes;
    }
}
