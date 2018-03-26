package com.dejans.proba3microservices;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;


public class OurORM {
    private ModelDefinitionTree model;

    private class Items {
        protected String[] columnNames;
        protected String[] itemNames;
    }

    private void setAllTypes(int position, PreparedStatement pSql, Object value, String type) throws SQLException {
        if (value == null) {
            if (type.equals("Integer")) {
                pSql.setNull(position, Types.INTEGER);
            } else if (type.equals("String")) {
                pSql.setNull(position, Types.VARCHAR);
            } else if (type.equals("LocalDate")) {
                pSql.setNull(position, Types.DATE);
            }
        }
        if (value instanceof Integer) {
            pSql.setInt(position, (Integer) value);
        } else if (value instanceof String) {
            pSql.setString(position, (String) value);
        } else if (value instanceof LocalDate) {
            pSql.setObject(position, value);
        }

    }

    private Connection connectStart() {
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dejans", "dejans",
                    "pinosava");
            con.setAutoCommit(false);
            return con;
        } catch (SQLException e) {

            System.out.println(e.getMessage());
        }
        return null;
    }

    private void connectEnd(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void addToSqlBatch(PreparedStatement pSql, Object[] paramArray, String[] paramTypeArray, int iLong)
            throws SQLException {
        try {
            for (int k = 0; k < iLong; k++) {
                setAllTypes(k + 1, pSql, paramArray[k], paramTypeArray[k]);
            }
            pSql.addBatch();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
    }

    private void executeUpdIns(String sqlText, Object[] paramArray, int iLong) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "dejans", "pinosava");
            PreparedStatement insertSql;
            con.setAutoCommit(false);
            insertSql = con.prepareStatement(sqlText);
            for (int k = 0; k < iLong; k++) {
                setAllTypes(k + 1, insertSql, paramArray[k], "String");
            }
            insertSql.executeUpdate();
            con.commit();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (con != null) {
                con.close();
            }
        }
    }

    public OurORM(ModelDefinitionTree model) {
        this.model = model;
    }

    public void updIns(DatabaseClassList<?> object) {
        if (object.typeClass().equals("List")) {
            // this is list of objects so this is 
            for (Object obj : object.getList()) {
                DatabaseClass dbObj = (DatabaseClass) obj;
                System.out.println(dbObj.toString());
                System.out.println(obj.getClass());
                try {
                    updIns(dbObj);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            // this is not list object so it's error
        }
    }

    public void updIns1(DatabaseClassList<?> object) {
        String[] xItems;
        String[] xTypes;
        String[] xPrimaryKey;
        String[] xPrimaryKeyType;
        String databaseType = new String();
        String[] xColumns;
        String tableName = new String();
        String className = null;
        String columns = new String();
        String insertParam = new String();
        String insertSqlTxt = new String();
        String SqlTxt = new String();
        String updateWhereSqlTxt = new String();
        String updateSetSqlTxt = new String();
        String updateSqlTxt = new String();
        String deleteWhereSqlTxt = new String();
        String deleteSqlTxt = new String();

        if (object.typeClass().equals("List")) {
            // this is list of objects so this is 
            xItems = object.getxItems();
            xTypes = object.getxType();
            xPrimaryKey = object.getxPrimaryKey();
            xPrimaryKeyType = object.getxPrimaryKeyType();

            Connection con = connectStart();
            PreparedStatement insertSql = null;
            PreparedStatement updateSql = null;
            PreparedStatement deleteSql = null;

            for (Object obj : object.getList()) {
                DatabaseClass dbObj = (DatabaseClass) obj;
                if (className != object.nameClassBase()) {
                    className = object.nameClassBase();
                    databaseType = model.getDatabaseType(className);
                    if (databaseType.equals("table")) {
                        tableName = model.getDatabaseTable(className);
                        for (int i = 0; i < xItems.length; i++) {
                            String itemName = xItems[i];
                            String type = xTypes[i];
                            String column = model.getColumn(className, itemName);
                            columns += "," + column;
                            insertParam += ",?";
                            System.out.println("Item:" + itemName + " Type:" + type);
                        }
                        try {
                            insertSqlTxt = "INSERT INTO " + tableName + " (" + columns.substring(1) + ") VALUES ("
                                    + insertParam.substring(1) + ")";
                            insertSql = con.prepareStatement(insertSqlTxt);
                        } catch (SQLException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                if (dbObj.getStatus().equals("I")) {
                    // Command: Insert
                    Object[] paramArray = new Object[xItems.length];
                    String[] paramTypeArray = new String[xItems.length];
                    for (int i = 0; i < xItems.length; i++) {
                        paramArray[i] = dbObj.get(xItems[i]);
                        paramTypeArray[i] = xTypes[i];
                    }
                    try {
                        addToSqlBatch(insertSql, paramArray, paramTypeArray, xItems.length);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (dbObj.getStatus().equals("U")) {
                    Object[] paramArray = new Object[4 * xItems.length];
                    String[] paramTypeArray = new String[4 * xItems.length];
                    int nA = -1;
                    updateSqlTxt = "";
                    updateSetSqlTxt = "";
                    for (int i = 0; i < xItems.length; i++) {
                        String itemName = xItems[i];
                        String type = xTypes[i];
                        String column = model.getColumn(className, itemName);
                        if (dbObj.isChanged(itemName)) {
                            Object value = dbObj.get(itemName);
                            updateSetSqlTxt += "," + column + "=?";
                            nA++;
                            paramArray[nA] = value;
                            paramTypeArray[nA] = type;
                        }
                    }
                    updateSetSqlTxt = updateSetSqlTxt.substring(1);
                    updateWhereSqlTxt = "";
                    for (int i = 0; i < xPrimaryKey.length; i++) {
                        String column = model.getColumn(className, xPrimaryKey[i]);
                        String type = xPrimaryKeyType[i];
                        Object value = dbObj.getValueForPrimaryKey(xPrimaryKey[i]);

                        if (value == null) {
                            updateWhereSqlTxt += " and " + column + " is null";
                        } else {
                            updateWhereSqlTxt += " and " + column + " = ?";
                            nA++;
                            paramArray[nA] = value;
                            paramTypeArray[nA] = type;
                        }
                    }
                    updateWhereSqlTxt = updateWhereSqlTxt.substring(4);
                    updateSqlTxt = "UPDATE " + tableName + " SET " + updateSetSqlTxt + " WHERE " + updateWhereSqlTxt;
                    try {
                        updateSql = con.prepareStatement(updateSqlTxt);
                        addToSqlBatch(updateSql, paramArray, paramTypeArray, nA + 1);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (dbObj.getStatus().equals( "D")) {
                    Object[] paramArray = new Object[2 * xItems.length];
                    String[] paramTypeArray = new String[2 * xItems.length];
                    int nA = -1;
                    deleteSqlTxt = "";
                    deleteWhereSqlTxt = "";
                    for (int i = 0; i < xPrimaryKey.length; i++) {
                        String column = model.getColumn(className, xPrimaryKey[i]);
                        String type = xPrimaryKeyType[i];
                        Object value = dbObj.getValueForPrimaryKey(xPrimaryKey[i]);

                        if (value == null) {
                            deleteWhereSqlTxt += " and " + column + " is null";
                        } else {
                            deleteWhereSqlTxt += " and " + column + " = ?";
                            nA++;
                            paramArray[nA] = value;
                            paramTypeArray[nA] = type;
                        }
                    }
                    deleteWhereSqlTxt = deleteWhereSqlTxt.substring(4);
                    deleteSqlTxt = "DELETE FROM " + tableName + " WHERE " + deleteWhereSqlTxt;
                    try {
                        deleteSql = con.prepareStatement(deleteSqlTxt);
                        addToSqlBatch(deleteSql, paramArray, paramTypeArray, nA + 1);
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
            try {
                if (insertSql != null) {
                    int[] countI = insertSql.executeBatch();
                }
                if (updateSql != null) {
                    int[] countU = updateSql.executeBatch();
                }
                if (deleteSql != null) {
                    int[] countD = deleteSql.executeBatch();
                }
                con.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                if (e.getNextException() != null) {
                    e.getNextException().printStackTrace();
                }
            }

        } else

        {
            // this is not list object so it's error
        }
    }

    public void updIns(DatabaseClass object) throws SQLException {
        long startTime = System.nanoTime();
        String nameObject = new String();
        String databaseType = new String();
        String tableName = new String();
        String itemName = null;
        if (object.typeClass().equals("Object")) {
            // this is  object so this is 
            System.out.println(object.nameClass());
            // get name of oobject
            nameObject = object.nameClass();
            if (object.getStatus().equals("I")) {
                // COmmand: Insert object
                // get database type (usual result: table)
                databaseType = model.getDatabaseType(nameObject);
                if (databaseType.equals("table")) {
                    // get table name
                    String columns = new String();
                    String param = new String();
                    int iLong = 0;
                    Object[] paramArray = new Object[10];
                    tableName = model.getDatabaseTable(nameObject);
                    System.out.println("Table:" + tableName);
                    itemName = model.nextItem(nameObject, itemName);
                    while (itemName != null) {
                        String column = model.getColumn(nameObject, itemName);
                        String itemType = model.getItemJavaType(nameObject, itemName);
                        columns += "," + column;
                        param += ",?";
                        paramArray[iLong] = object.get(itemName);
                        iLong++;
                        System.out.println("Item:" + itemName + ":" + column);
                        itemName = model.nextItem(nameObject, itemName);
                    }
                    String sqlInsert = "INSERT INTO " + tableName + " (" + columns.substring(1) + ") VALUES ("
                            + param.substring(1) + ")";
                    System.out.println(sqlInsert);
                    executeUpdIns(sqlInsert, paramArray, iLong);

                }

            } else if (object.getStatus().equals("U") || object.getStatus().equals("FU")) {
            }
        } else {
            // this is not object so it's error
            // ... the code being measured ...    
            long estimatedTime = System.nanoTime() - startTime;
            System.out.println(estimatedTime);
        }
    }

    public void selectObjects(QueryBuilder getCommand, DatabaseClassList<?> object) throws Exception {

        String selectSqlTxt = new String();
        String[] xItems = null;
        String[] xTypes = null;
        String[] xPrimaryKey;
        String[] xPrimaryKeyType;
        String databaseType = new String();
        String tableName = new String();
        String className = new String();
        String columns = new String();
        boolean forUpdate;

        Connection con = connectStart();

        if (object.typeClass().equals("List")) {
            if (getCommand.selectAllItems()) {
                xItems = object.getxItems();
                xTypes = object.getxType();
            } else {
                xItems = getCommand.getSelectedItems();
                xTypes = getCommand.getSelectedTypes();
            }
            xPrimaryKey = object.getxPrimaryKey();
            xPrimaryKeyType = object.getxPrimaryKeyType();
            className = object.nameClassBase();
            databaseType = model.getDatabaseType(className);
            if (databaseType.equals("table")) {
                tableName = model.getDatabaseTable(className);
                for (int i = 0; i < xItems.length; i++) {
                    String itemName = xItems[i];
                    String type = xTypes[i];
                    String column = model.getColumn(className, itemName);
                    columns += "," + column;
                }
                columns = columns.substring(1);
            }
            selectSqlTxt = "SELECT " + columns + " FROM " + tableName + " WHERE " + getCommand.whereClause();
        }

        try {
            PreparedStatement sqlSts = null;
            sqlSts = con.prepareStatement(selectSqlTxt);
            int iNum = 0;
            for (QueryBuilder.ObjType obj : getCommand.params()) {
                iNum++;
                setAllTypes(iNum, sqlSts, obj.object, obj.type);
            }
            forUpdate = getCommand.getForUpdate();

            ResultSet rs = sqlSts.executeQuery();

            while (rs.next()) {
                DatabaseClass obj = object.newObject();
                for (int k = 0; k < xItems.length; k++) {
                    if (xTypes[k].equals("String")) {
                        String tmp = rs.getString(xItems[k]);
                        obj.set(xItems[k], tmp);
                    } else if (xTypes[k].equals("Integer")) {
                        Integer tmp = rs.getInt(xItems[k]);
                        obj.set(xItems[k], tmp);
                    } else if (xTypes[k].equals("LocalDate")) {
                        LocalDate tmp = rs.getObject(xItems[k], LocalDate.class);
                        obj.set(xItems[k], tmp);
                    }

                }
                String lStatus;
                if (forUpdate) {
                    lStatus = "FU";
                } else {
                    lStatus = "S";
                }
                obj.setStatus(lStatus);
                object.add(obj);
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            connectEnd(con);
        }

    }

}