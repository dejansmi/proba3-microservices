package com.dejans.proba3microservices;

import java.util.TreeMap;

public class ModelDefinitionTree {
    private TreeMap<String, String> modelTree;
    private String currentObject;
    private String currentDatabase;
    private String defaultDatabase;

    public ModelDefinitionTree(TreeMapYamlParse yamlParse) {
        modelTree = yamlParse.getTreeMap();
    }

    private String getDatabaseForObject(String object) {
        String key = new String();
        if (object.equals(currentDatabase) && currentDatabase != null) {
            return currentDatabase;
        }
        if (defaultDatabase == null) {
            key = ".{.Databases.{.default";
            String defaultDatabase = modelTree.get(key);
            if (defaultDatabase == null) {
                // if default database dosn't set in file Databases.yml postgres is default
                this.defaultDatabase = "postgres";
            } else {
                this.defaultDatabase = defaultDatabase;
            }
        }
        if (currentDatabase == null || !currentDatabase.equals(object)) {
            currentDatabase = object;
        }
        // check ecoecial definition database for object
        key = ".{.Databases.{.objects.{." + object;
        currentDatabase = modelTree.get(key);
        if (currentDatabase == null) {
            currentDatabase = defaultDatabase;
        }
        return currentDatabase;
    }

    public String getDatabaseType(String object) {
        String key = new String();
        key = ".{.Models.{." + object + ".{.server.{.database.{.type";
        String type = modelTree.get(key);
        return type;
    }

    public String getDatabaseTable(String object) {
        String key = new String();
        String databaseName;
        if (currentDatabase == null || !object.equals(currentObject)) {
            databaseName = getDatabaseForObject(object);
        } else {
            databaseName = currentDatabase;
        }
        key = ".{.Models.{." + object + ".{.server.{.database.{.table.{.databases.{." + databaseName;
        String table = modelTree.get(key);
        if (table == null) {
            // if table name dosn't define for database let use default 
            key = ".{.Models.{." + object + ".{.server.{.database.{.table.{.name";
            table = modelTree.get(key);
        }
        return table;
    }

    public String getColumn(String object, String element) {
        String key = new String();
        key = ".{.Models.{." + object + ".{.items.{.properties.{." + element + ".{.column";
        String column = modelTree.get(key);
        return column;
    }

    public String getItemJavaType(String object, String element) {
        String key = new String();
        key = ".{.Models.{." + object + ".{.items.{.properties.{." + element + ".{.java";
        String itemType = modelTree.get(key);
        return itemType;
    }


    public String nextItem(String object, String item) {
        String key = new String();
        String lItem = new String();
        if (item == null || item.equals("")) {
            key = ".{.Models.{." + object + ".{.items.{.properties.{.";
        } else {
            key = ".{.Models.{." + object + ".{.items.{.properties.{."+ item + "@";
        }
        String mask = ".{.Models.{." + object + ".{.items.{.properties.{.";
        key = modelTree.higherKey(key);
        if (key.indexOf(mask)== 0) {
            // SR: i dalje je item na redu
            key = key.substring(mask.length());
            int point = key.indexOf('.');
            lItem = key.substring(0, point);
            return lItem;
        } else {
            // next raw in map tree isn't item so it's end 
            return null;
        }

    }

    public class LengthValidate {
        int length;
        boolean validate;
        String action;
        String errormessage; 
        String errorcode;

        public LengthValidate(){
            // Default values
            validate = true;
            action = "error";
            errormessage = "${object}.${item} is greater than ${length} characters";
            errorcode = "VAL-001";
        }
    }

    public LengthValidate lengthValidate (String object, String item) {
        String keyLength = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{.length";
        String length = modelTree.get(keyLength);
        if (length==null) {
            // length not defined
            LengthValidate lval = new LengthValidate();
            lval.validate = false;
            lval.action = "";
            lval.errormessage = "";
            lval.errorcode = "";
            return lval;
        } else {
            LengthValidate lval = new LengthValidate();
            lval.length = Integer.parseInt(length);
            
            String key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{.length.{.lengthValidate.{.set.{.validate";
            String value = modelTree.get(key);
            if (value != null) {
                lval.validate = Boolean.parseBoolean(value);
            }
            key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{.length.{.lengthValidate.{.set.{.action";
            value = modelTree.get(key);
            if (value != null) {
                lval.action = value;
            }
            key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{.length.{.lengthValidate.{.set.{.errornessage";
            value = modelTree.get(key);
            if (value != null) {
                lval.errormessage = value;
                lval.errormessage = lval.errormessage.replace("${object}", object).replace(("${item}"), item).replace("${length}", length);
            }
            key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{.length.{.lengthValidate.{.set.{.errorcode";
            value = modelTree.get(key);
            if (value != null) {
                lval.errorcode = value;
            }
            return lval;
        }

    }
}