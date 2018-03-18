package com.dejans.proba3microservices;

import java.time.LocalDate;

public class Pet implements DatabaseClass {
    private Integer id;
    private boolean iddefined;
    private String name;
    private boolean namedefined;
    private String tag;
    private boolean tagdefined;
    private LocalDate born;
    private boolean borndefined;
    private Pet OLD;
    // I - Insert
    // U - Update
    // D - Delete 
    // FU- For Update
    private String status;

    public Pet() {
        status = null;
        iddefined = false;
        namedefined = false;
        tagdefined = false;
        borndefined = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        if (!this.iddefined) {
            this.id = id;
            this.iddefined = true;
            return;
        }
        if (this.id == id)
            return;
        if (status != null && (status.equals("FU") || status.equals("U"))) {
            status = "U";
            if (!this.iddefined) {
                this.id = id;
                this.iddefined = true;
            } else {
                if (this.id == id)
                    return;
                if (OLD == null) {
                    OLD = new Pet();
                }
                OLD.id = this.id;
                OLD.iddefined = true;
                this.id = id;

            }

        } else if (status == null || !status.equals("D")) {
            this.id = id;
            this.iddefined = true;
        }
        // SR: for status D(elete) nije dozvoljena izmena
        return;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!this.namedefined) {
            this.name = name;
            this.namedefined = true;
            return;
        }
        if (this.name == null && name == null)
            return;
        if (this.name != null && this.name.equals(name) && this.namedefined)
            return;
        if (status != null && (status.equals("FU") || status.equals("U"))) {
            status = "U";
            if (!this.namedefined) {
                this.name = name;
                this.namedefined = true;
            } else {
                if (this.name != null && this.name.equals(name))
                    return;
                if (OLD == null) {
                    OLD = new Pet();
                }
                OLD.name = this.name;
                OLD.namedefined = true;
                this.name = name;

            }

        } else if (status == null || !status.equals("D")) {
            this.name = name;
            this.namedefined = true;
        }
        // SR: for status D(elete) nije dozvoljena izmena
        return;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        if (!this.tagdefined) {
            this.tag = tag;
            this.tagdefined = true;
            return;
        }
        if (this.tag == null && tag == null)
            return;
        if (this.tag != null && this.tag.equals(tag) && this.tagdefined)
            return;
        if (status != null && (status.equals("FU") || status.equals("U"))) {
            status = "U";
            if (!this.tagdefined) {
                this.tag = tag;
                this.tagdefined = true;
            } else {
                if (this.tag != null && this.tag.equals(tag))
                    return;
                if (OLD == null) {
                    OLD = new Pet();
                }
                OLD.tag = this.tag;
                OLD.tagdefined = true;
                this.tag = tag;

            }

        } else if (status == null || !status.equals("D")) {
            this.tag = tag;
            this.tagdefined = true;
        }
        // SR: for status D(elete) nije dozvoljena izmena
        return;

    }

    public LocalDate getBorn() {
        return born;
    }

    public void setBorn(LocalDate born) {
        if (!this.borndefined) {
            this.born = born;
            this.borndefined = true;
            return;
        }
        if (this.born == null && born == null)
            return;
        if (this.born != null && this.born.equals(born) && this.borndefined)
            return;
        if (status != null && (status.equals("FU") || status.equals("U"))) {
            status = "U";
            if (!this.borndefined) {
                this.born = born;
                this.borndefined = true;
            } else {
                if (this.born != null && this.born.equals(born))
                    return;
                if (OLD == null) {
                    OLD = new Pet();
                }
                OLD.born = this.born;
                OLD.borndefined = true;
                this.born = born;

            }

        } else if (status == null || !status.equals("D")) {
            this.born = born;
            this.borndefined = true;
        }
        // SR: for status D(elete) nije dozvoljena izmena
        return;

    }

    public Pet getOLD() {
        return OLD;
    }

    public void setOLD(Pet oLD) {
        this.OLD = oLD;
        // only one level of OLD so we cancel OLD for OLD object
        this.OLD.OLD = null;
    }

    public String typeClass() {
        return "Object";
    }

    public String nameClass() {
        return "Pet";
    }

    public String getStatus() {
        if (OLD == null && status == null) {
            status = "I";
        }
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object get(String itemName) {
        if (itemName.equals("id")) {
            return id;
        } else if (itemName.equals("name")) {
            return name;
        } else if (itemName.equals("tag")) {
            return tag;
        } else if (itemName.equals("born")) {
            return born;
        }
        return null;
    }

    public void set(String itemName, Object value) {
        if (itemName.equals("id")) {
            setId((Integer) value);
        } else if (itemName.equals("name")) {
            setName((String) value);
        } else if (itemName.equals("tag")) {
            setTag((String) value);
        } else if (itemName.equals("born")) {
            setBorn((LocalDate) value);
        }
        return;
    }

    public boolean isChanged(String itemName) {
        if (OLD == null)
            return false;
        if (itemName.equals("id")) {
            return OLD.iddefined;
        } else if (itemName.equals("name")) {
            return OLD.namedefined;
        } else if (itemName.equals("tag")) {
            return OLD.tagdefined;
        } else if (itemName.equals("born")) {
            return OLD.borndefined;
        }
        return false;
    }

    public Object getValueForPrimaryKey(String itemName) {
        if (itemName.equals("id")) {
            if (OLD != null && OLD.iddefined) {
                return OLD.id;
            } else {
                return id;
            }
        } else if (itemName.equals("name")) {
            if (OLD != null && OLD.namedefined) {
                return OLD.name;
            } else {
                return name;
            }
        } else if (itemName.equals("tag")) {
            if (OLD != null && OLD.tagdefined) {
                return OLD.tag;
            } else {
                return tag;
            }
        } else if (itemName.equals("born")) {
            if (OLD != null && OLD.borndefined) {
                return OLD.born;
            } else {
                return born;
            }
        }
        return null;
    }

}