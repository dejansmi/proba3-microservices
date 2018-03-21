package com.dejans.proba3microservices;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
 
public class YamlMap {

    protected class EoI { // ElementOfIndex
        protected String valueString;
        protected Integer valueInteger;
        protected Date valueDate;
        protected Double valueDouble;
        protected Boolean valueBoolean;

        protected boolean isChild;
        protected ArrayList<String> list;
        private int num = 0;
        

        protected EoI (String valueString)  {
            this.valueString = valueString;
            this.isChild = false;
        }

        protected EoI (Integer valueInteger)  {
            this.valueInteger = valueInteger;
            this.isChild = false;
        }

        protected EoI (Date valueDate)  {
            this.valueDate = valueDate;
            this.isChild = false;
        }

        protected EoI (Double valueDouble)  {
            this.valueDouble = valueDouble;
            this.isChild = false;
        }

        protected EoI (Boolean valueBoolean)  {
            this.valueBoolean = valueBoolean;
            this.isChild = false;
        }
        

        protected  EoI () {
            num = 0;
            list = new ArrayList<String>();
        }

        protected void add (String chileName) {
            list.add(num, chileName);
            num ++;
        }

    }
    private HashMap<String, EoI> index;
    private EoI list;

    public  YamlMap ()  {
        index = new HashMap<String, EoI>();
        index.put("A.A", new EoI("A1"));
        index.put("A.B", new EoI("A2"));
        index.put("A.C", new EoI("A3"));
        index.put("B.A", new EoI("B1"));
        index.put("B.B", new EoI("B2"));
        index.put("B.C.A", new EoI("B3"));
        index.put("B.C.B", new EoI("B4"));
        index.put("B.C.C", new EoI("B5"));
        index.put("B.C.D", new EoI("B6"));
        index.put("C", new EoI("C1"));
        list = new EoI();
        list.add("A");
        list.add("B");
        list.add("C");
        this.index.put("/", list);
        list = new EoI();
        list.add("A");
        list.add("B");
        list.add("C");
        this.index.put("A", list);
        list = new EoI();
        list.add("A");
        list.add("B");
        list.add("C");
        this.index.put("B", list);
        list = new EoI();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        this.index.put("B.C", list);
   }

    public String PrintHashMapString () {
        String pom = null;
        ArrayList<EoI> list = new ArrayList<EoI>();
        EoI oEoI;

        oEoI = index.get("/");

        return pom;
    }
}