package com.dejans.proba3microservices;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ConfigDomain {

    private Domains domains;
    private Address address;

    public class Address {
        private Map<String, String> address;
        private Domains domains;

        public Map<String, String> getAddress() {
            return address;
        }

        public void setAddress(Map<String, String> address) {
            this.address = address;
        }

        public Domains getDomains() {
            return domains;
        }

        public void setDomains(Domains domains) {
            this.domains = domains;
        }

    }

    public class Domains {
        @JsonProperty("xfirst")
        private String first;
        private String second;
        private String[] c;
        private List<aList> d;

        //private ArrayList<ArrayThird> third;
        private ArrayList<aIb> third;

        public String getFirst() {
            return this.first;
        }

        public String getSecond() {
            return this.second;
        }

        public List<aIb> getThird() {
            return this.third;
        }

        public void setThird(ArrayList<aIb> third) {
            this.third = third;
        }

        /*
        public ArrayList<ArrayThird> getThird() {
            return this.third;
        }
        
        */
        public void setFirst(String first) {
            this.first = first;
        }

        public void setSecond(String second) {
            this.second = second;
        }

        public String[] getC() {
            return this.c;
        }

        public void setC(String[] c) {
            this.c = c;
        }

        public List<aList> getD() {
            return this.d;
        }

        public void setd(List<aList> d) {
            this.d = d;
        }

        public String toString() {
            String pom = new String();
            for (aIb str : third) {
                pom += str.a + ">" + str.b;
                pom += str.toString();
            }
            pom += " First:" + first + " Second:" + second;
            if (c != null) {
                for (String var : c) {
                    pom += "!" + var;
                }
            }
            for (aList al : d) {
                pom += al.toString();
            }
            return pom;
        }
        /*
        public void setThird(ArrayList<ArrayThird> third) {
            this.third = third;
        }
        */

    }

    static public class aList {
        private String[] listStr;

        public String[] getListStr() {
            return this.listStr;
        }

        public void setListStr(String[] listStr) {
            this.listStr = listStr;
        }

        public String toString() {
            String pom = "&";
            if (listStr != null) {
                for (String str : listStr) {
                    pom += " !" + str;
                }
            }
            return pom;
        }

    }

    static public class aIb {
        private String a;
        private String b;
        private String[] c;

        public String geA() {
            return this.a;
        }

        public String getB() {
            return this.b;
        }

        public void setA(String a) {
            this.a = a;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String[] getC() {
            return this.c;
        }

        public void setC(String[] c) {
            this.c = c;
        }

        public String toString() {
            String pom = new String();
            if (c == null)
                return "@";
            for (String var : c) {
                pom += "#" + var;
            }
            return pom;
        }

    }

    public class ArrayThird {
        private ArrayList<String> arr;

        @JsonCreator
        public ArrayThird(final ArrayList<String> arr) {
            this.arr = arr;
        }

        @JsonCreator
        public ArrayThird(final String strarr) {
            this.arr.add(0, strarr);
        }

        public ArrayList<String> getArr() {
            return arr;
        }

        public void setArr(ArrayList<String> arr) {
            this.arr = arr;
        }

    }

    public Domains getDomains() {
        return domains;
    }

    public void setDomains(Domains domains) {
        this.domains = domains;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}