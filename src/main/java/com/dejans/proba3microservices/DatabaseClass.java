package com.dejans.proba3microservices;

public interface DatabaseClass {

    public String typeClass ();
    public String nameClass ();
    public String getStatus();
    public Object get(String itemName);
    public Object getValueForPrimaryKey(String itemName);
    public boolean isChanged(String itemName);
    public void set(String itemName, Object value) throws Exception;
    public void setStatus(String status);


}