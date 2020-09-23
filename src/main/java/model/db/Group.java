package model.db;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "group")
public class Group {

    private int id;
    private String name;
    private String description;

    public Group() {
        this.name = "prova";
    }

    public Group(final String name) {
        this.setName(name);
        this.description = "";
        createId();
    }


    private void createId() {
        // TODO Auto-generated method stub
    }


    public final String getName() {
        return name;
    }


    public final void setName(final String name) {
        this.name = name;
    }
}
