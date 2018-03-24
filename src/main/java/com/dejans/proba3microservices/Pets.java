package com.dejans.proba3microservices;

import java.util.ArrayList;
import java.util.List;


public class Pets implements DatabaseClassList<Pet> {
    private List<Pet> pets;

    private String[] xItems = {"born", "id", "name", "tag" };
    private String[] xType = {"LocalDate", "Integer", "String", "String"  };
    private String[] xPrimaryKey = {"id" };
    private String[] xPrimaryKeyType = {"Integer" };

    public Pets () {
        pets = new ArrayList<Pet>();
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public String typeClass() {
        return "List";
    }

    public List<Pet> getList() {
        return pets;
    }

    public String nameClass() {
        return "Pets";
    }

    public String nameClassBase() {
        return "Pet";
    }

    public String[] getxItems() {
        return xItems;
    }

    public String[] getxType() {
        return xType;
    }

    public String[] getxPrimaryKey() {
        return xPrimaryKey;
    }

    public String[] getxPrimaryKeyType() {
        return xPrimaryKeyType;
    }

    public DatabaseClass newObject() {
        return new Pet();
    }

    public void add(Object obj) {
        pets.add((Pet) obj);
    }
}
