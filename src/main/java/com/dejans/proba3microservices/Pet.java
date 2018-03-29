package com.dejans.proba3microservices;

import java.time.LocalDate;


import lombok.val;


public class Pet implements DatabaseClass  {
    private LocalDate born;
    private boolean borndefined;
    private Integer id;
    private boolean iddefined;
    private String name;
    private boolean namedefined;
    private String tag;
    private boolean tagdefined;
    private Pet OLD;
    private String status;

    public Pet() {
      status = null;
      borndefined = false;
      iddefined = false;
      namedefined = false;
      tagdefined = false;
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
            if (this.born != null && this.born.equals(name))
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

    public void setName(String name) throws Exception {
        if (!this.namedefined) {
            ValidateByDefinition.setValidate("Pet", "name","String", name);
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
                ValidateByDefinition.setValidate("Pet", "name","String", name);
                this.name = name;
                this.namedefined = true;
        } else {
            if (this.name != null && this.name.equals(name))
                return;
            if (OLD == null) {
                OLD = new Pet();
            }
            ValidateByDefinition.setValidate("Pet", "name","String", name);
            OLD.name = this.name;
            OLD.namedefined = true;
            this.name = name;
            this.tagdefined = true;

            }

        } else if (status == null || !status.equals("D")) {
            ValidateByDefinition.setValidate("Pet", "name","String", name);
            this.name = name;
            this.namedefined = true;
        }
        // SR: for status D(elete) nije dozvoljena izmena
        return;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) throws Exception { 
        if (!this.tagdefined) {
            ValidateByDefinition.setValidate("Pet", "tag","String", tag);
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
                ValidateByDefinition.setValidate("Pet", "tag","String", tag);
                this.tag = tag;
                this.tagdefined = true;
        } else {
            if (this.tag != null && this.tag.equals(name))
                return;
            if (OLD == null) {
                OLD = new Pet();
            }
            ValidateByDefinition.setValidate("Pet", "tag","String", tag);
            OLD.tag = this.tag;
            OLD.tagdefined = true;
            this.tag = tag;
            this.tagdefined = true;

            }

        } else if (status == null || !status.equals("D")) {
            ValidateByDefinition.setValidate("Pet", "tag","String", tag);
            this.tag = tag;
            this.tagdefined = true;
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
        if (itemName.equals("born")) {
            return born;
        } else if (itemName.equals("id")) {
            return id;
        } else if (itemName.equals("name")) {
            return name;
        } else if (itemName.equals("tag")) {
            return tag;
        }
        return null;
    }

    public void set(String itemName, Object value) throws Exception {
        if (itemName.equals("born")) {
            setBorn((LocalDate) value);
        } else if (itemName.equals("id")) {
            setId((Integer) value);
        } else if (itemName.equals("name")) {
            setName((String) value);
        } else if (itemName.equals("tag")) {
            setTag((String) value);
        }
        return;
    }

    public boolean isChanged(String itemName) {
        if (OLD == null)
            return false;
        if (itemName.equals("born")) {
            return OLD.borndefined;
        } else if (itemName.equals("id")) {
            return OLD.iddefined;
        } else if (itemName.equals("name")) {
            return OLD.namedefined;
        } else if (itemName.equals("tag")) {
            return OLD.tagdefined;
        }
        return false;
    }

    public Object getValueForPrimaryKey(String itemName) {
        if (itemName.equals("born")) {
            if (OLD != null && OLD.borndefined) {
                return OLD.born;
            } else {
                return born;
            }
        } else if (itemName.equals("id")) {
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
        }
        return null;
    }


}
