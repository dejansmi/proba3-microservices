package com.dejans.proba3microservices;

import java.util.ArrayList;

public class QueryBuilder {
    public class ObjType {
        public Object object;
        public String type;
    }

    private String className = new String();
    private ModelDefinitionTree model;
    private String databaseType = new String();
    private String tableName = new String();
    private String whereClause = new String();
    private String columnName = new String();
    private boolean forUpdate;
    private boolean selectNotAllItems;
    private String[] xItems;
    private String[] xType;

    int num;
    ArrayList<ObjType> objArr;

    public QueryBuilder(ModelDefinitionTree model, String className) {
        this.className = className;
        this.model = model;
        databaseType = model.getDatabaseType(className);
        if (databaseType.equals("table")) {
            tableName = model.getDatabaseTable(className);
        } else {
            tableName = null;
        }
        num = -1;
        objArr = new ArrayList<ObjType>();
        this.selectNotAllItems = false;
    }

    private void check() {
        if (className == null) {
            // TODO Auto-generated catch block
            throw new java.lang.Error("Class is null in QueryBuilder");
        }

    }

    public QueryBuilder item(String itemName) {
        check();
        if (tableName != null) {
            columnName = model.getColumn(className, itemName);
            whereClause += columnName;
        }
        return this;
    }

    public QueryBuilder gt() {
        whereClause += ">";
        return this;
    }

    public QueryBuilder lt() {
        whereClause += "<";
        return this;
    }

    public QueryBuilder ge() {
        whereClause += ">=";
        return this;
    }

    public QueryBuilder le() {
        whereClause += "<=";
        return this;
    }

    public QueryBuilder equal() {
        whereClause += "=";
        return this;
    }

    public QueryBuilder eq() {
        whereClause += "=";
        return this;
    }

    public QueryBuilder ne() {
        whereClause += "!=";
        return this;
    }

    // left parentheses
    public QueryBuilder lp() {
        whereClause += "(";
        return this;
    }

    // right parentheses
    public QueryBuilder rp() {
        whereClause += "(";
        return this;
    }

    public QueryBuilder and() {
        whereClause += " AND ";
        return this;
    }

    public QueryBuilder or() {
        whereClause += " OR ";
        return this;
    }

    public QueryBuilder not() {
        whereClause += " NOT ";
        return this;
    }

    public QueryBuilder like() {
        whereClause += " LIKE ";
        return this;
    }

    public QueryBuilder constant(Integer param) {
        num++;
        ObjType tmp = new ObjType();
        tmp.object = (Integer) param;
        tmp.type = "Integer";
        objArr.add(tmp);
        whereClause += "? ";
        return this;
    }

    public QueryBuilder constant(String param) {
        num++;
        ObjType tmp = new ObjType();
        tmp.object = (String) param;
        tmp.type = "String";
        objArr.add(tmp);
        whereClause += "? ";
        return this;
    }

    public QueryBuilder forUpdate() {
        this.forUpdate = true;
        return this;
    }

    public boolean getForUpdate() {
        return forUpdate;
    }

    public String whereClause() {
        return whereClause;
    }

    public Object paramNum(int iNum) {
        return objArr.get(iNum);
    }

    public ArrayList<ObjType> params() {
        return objArr;
    }

    public boolean selectAllItems() {
        return !selectNotAllItems;
    }

    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7, String item8, String item9, String item10, String item11, String item12, String item13,
            String item14, String item15) {
        return selectItems(15, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13,
                item14, item15);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7, String item8, String item9, String item10, String item11, String item12, String item13,
            String item14) {
        return selectItems(14, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13,
                item14, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7, String item8, String item9, String item10, String item11, String item12, String item13) {
        return selectItems(13, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7, String item8, String item9, String item10, String item11, String item12) {
        return selectItems(12, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7, String item8, String item9, String item10, String item11) {
        return selectItems(11, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7, String item8, String item9, String item10) {
        return selectItems(10, item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7, String item8, String item9) {
        return selectItems(9, item1, item2, item3, item4, item5, item6, item7, item8, item9, null, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7, String item8) {
        return selectItems(8, item1, item2, item3, item4, item5, item6, item7, item8, null, null, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6,
            String item7) {
        return selectItems(7, item1, item2, item3, item4, item5, item6, item7, null, null, null, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5, String item6) {
        return selectItems(6, item1, item2, item3, item4, item5, item6, null, null, null, null, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4, String item5) {
        return selectItems(5, item1, item2, item3, item4, item5, null, null, null, null, null, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3, String item4) {
        return selectItems(4, item1, item2, item3, item4, null, null, null, null, null, null, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2, String item3) {
        return selectItems(3, item1, item2, item3, null, null, null, null, null, null, null, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1, String item2) {
        return selectItems(2, item1, item2, null, null, null, null, null, null, null, null, null, null, null,
                null, null);
    }
    public QueryBuilder selectItems(String item1) {
        return selectItems(1, item1, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null);
    }

    private QueryBuilder selectItems(int len, String item1, String item2, String item3, String item4, String item5,
            String item6, String item7, String item8, String item9, String item10, String item11, String item12,
            String item13, String item14, String item15) {
        if (xItems == null) {
            xItems = new String[len];
            xType = new String[len];
        }
        selectNotAllItems = true;
        if (len > 0) {
            xItems[0] = item1;
            xType[0] = model.getItemJavaType(className, item1);
        }
        if (len > 1) {
            xItems[1] = item2;
            xType[1] = model.getItemJavaType(className, item2);
        }
        if (len > 2) {
            xItems[2] = item3;
            xType[2] = model.getItemJavaType(className, item3);
        }
        if (len > 3) {
            xItems[3] = item4;
            xType[3] = model.getItemJavaType(className, item4);
        }
        if (len > 4) {
            xItems[4] = item5;
            xType[4] = model.getItemJavaType(className, item5);
        }
        if (len > 5) {
            xItems[5] = item6;
            xType[5] = model.getItemJavaType(className, item6);
        }
        if (len > 6) {
            xItems[6] = item7;
            xType[6] = model.getItemJavaType(className, item7);
        }
        if (len > 7) {
            xItems[7] = item8;
            xType[7] = model.getItemJavaType(className, item8);
        }
        if (len > 8) {
            xItems[8] = item9;
            xType[8] = model.getItemJavaType(className, item9);
        }
        if (len > 9) {
            xItems[9] = item10;
            xType[9] = model.getItemJavaType(className, item10);
        }
        if (len > 10) {
            xItems[10] = item11;
            xType[10] = model.getItemJavaType(className, item11);
        }
        if (len > 11) {
            xItems[11] = item12;
            xType[11] = model.getItemJavaType(className, item12);
        }
        if (len > 12) {
            xItems[12] = item13;
            xType[12] = model.getItemJavaType(className, item13);
        }
        if (len > 13) {
            xItems[13] = item14;
            xType[13] = model.getItemJavaType(className, item14);
        }
        if (len > 14) {
            xItems[14] = item15;
            xType[14] = model.getItemJavaType(className, item15);
        }

        return this;
    }

    public String[] getSelectedItems () {
        return xItems;
    }

    public String[] getSelectedTypes () {
        return xType;
    }

}
