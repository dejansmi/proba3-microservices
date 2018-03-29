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

    public boolean getItemRequired(String object, String item) {
        String key = new String();
        key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{.required";
        String itemBool = modelTree.get(key);
        if (itemBool == null) return false;
        return itemBool.equals("true");
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
            key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + "@";
        }
        String mask = ".{.Models.{." + object + ".{.items.{.properties.{.";
        key = modelTree.higherKey(key);
        if (key.indexOf(mask) == 0) {
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

    public class PropertiesValidate {
        String valueProperty;
        boolean validate;
        String action;
        String errormessage;
        String errorcode;
        Integer show;
        String property;

        public PropertiesValidate(String property) {
            // Default values
            validate = true;
            action = "error";
            errormessage = "Unknown validate error";
            errorcode = "VAL-000-500";
            show = 3;
            this.property = property;
        }
    }

    public PropertiesValidate propertyValidate(String property, String object, String item) {
        String keyProperty = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{." + property;
        String propertyValue = modelTree.get(keyProperty);
        if (propertyValue == null) {
            // length not defined
            PropertiesValidate lval = new PropertiesValidate(property);
            lval.validate = false;
            lval.action = "";
            lval.errormessage = "";
            lval.errorcode = "";
            lval.show = 0;
            return lval;
        } else {
            PropertiesValidate lval = new PropertiesValidate(property);
            lval.valueProperty = propertyValue;

            String key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{." + property + "Validate.{.set.{.validate";
            String value = modelTree.get(key);
            if (value != null) {
                lval.validate = Boolean.parseBoolean(value);
            }
            key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{." + property + "Validate.{.set.{.action";
            value = modelTree.get(key);
            if (value != null) {
                lval.action = value;
            }
            key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{." + property + "Validate.{.set.{.errormessage";
            value = modelTree.get(key);
            if (value != null) {
                lval.errormessage = value;
                lval.errormessage = lval.errormessage.replace("${object}", object).replace(("${item}"), item)
                        .replace("${property}", property);
            }
            key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{." + property + "Validate.{.set.{.errorcode";
            value = modelTree.get(key);
            if (value != null) {
                lval.errorcode = value;
            }
            key = ".{.Models.{." + object + ".{.items.{.properties.{." + item + ".{." + property + "Validate.{.set.{.show";
            value = modelTree.get(key);
            if (value != null) {
                lval.show = Integer.parseInt(value);
            }
            return lval;
        }

    }
}