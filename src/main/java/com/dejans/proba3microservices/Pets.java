package com.dejans.proba3microservices;

import java.util.ArrayList;
import java.util.List;

public class Pets implements DatabaseClassList<Pet> {
    private List<Pet> pets;

    private String[] xItems = { "id", "name", "tag", "born" };
    private String[] xType = { "Integer", "String", "String", "LocalDate" };
    private String[] xPrimaryKey = { "id" };
    private String[] xPrimaryKeyType = { "Integer" };

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
        //DatabaseClass tmp = new Pet();
        return new Pet();
    }

    public void add(Object obj) {
        pets.add((Pet) obj);
    }


}