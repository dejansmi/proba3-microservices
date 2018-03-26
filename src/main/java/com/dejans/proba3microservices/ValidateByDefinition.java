package com.dejans.proba3microservices;

import java.util.Arrays;


public class ValidateByDefinition {
    private ModelDefinitionTree model = null;
    private static ValidateByDefinition instance = null;

    // this is Singleton class
    public static ValidateByDefinition getInstance() {
        if (instance == null) {
            instance = new ValidateByDefinition();
        }
        return instance;
    }

    protected ValidateByDefinition() {
    }

    public void setModel(ModelDefinitionTree model) {
        this.model = model;
    }

    private void lengthValidate(String object, String item, int length) throws Error, Exception {
        ModelDefinitionTree.LengthValidate lval = model.lengthValidate(object, item);
        if (lval.validate) {
            ExceptionHandlings.getInstance().addThrow("COD-001", "FIRST EXCEPTION", 0);
        }
    }

    public void setValidate(String object, String item, String type, Object value) throws Exception {
        System.out.println("USAO U SET VALIDATE");
        System.out.println("USAO U SET VALIDATE " + object + "." + item);
        System.out.println("USAO U SET VALIDATE");
        System.out.println("USAO U SET VALIDATE");
        try {
            if (type.equals("String")) {
                if (value != null && ((String) value).length() > 0) {
                    lengthValidate(object, item, ((String) value).length());

                }
            }
        } catch (Exception e) {
            ExceptionHandlings.getInstance().catchHandlings(e);

        } catch (Error e) {
            ExceptionHandlings.getInstance().catchHandlings(e);
        }

    }
}