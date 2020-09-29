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

    /**
     * getter Name.
     * @return name
     */
    public final String getName() {
        return name;
    }

    /**
     * setter name.
     * @param name
     */
    public final void setName(final String name) {
        this.name = name;
    }

    /**
     * getter description.
     * @return description
     */
    public final String getDescription() {
        return description;
    }

    /**
     * setter description.
     * @param description
     */
    public final void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        final Group other = (Group) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
