package model.db;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "group")
public class Group {

    private String name;
    private String description;

    public Group() {
        this.name = "prova";
        this.description = "prova";
    }

    public Group(final String name) {
        this.setName(name);
        this.setDescription("");
    }

    public Group(final String name, final String description) {
        this.setName(name);
        this.setDescription(description);
    }


    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }
}
