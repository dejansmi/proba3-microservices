package com.dejans.proba3microservices;


public class ValidateByDefinition {
    private ModelDefinitionTree model = null;
    private static ValidateByDefinition instance = null;

    // this is Singleton class
    private static ValidateByDefinition getInstance() {
        if (instance == null) {
            instance = new ValidateByDefinition();
        }
        return instance;
    }

    protected ValidateByDefinition() {
    }


    public static void setValidate (String object, String item, String type, Object value) throws Exception {
        getInstance().setValidateInternal (object, item, type, value);
    }

    public static void setModel(ModelDefinitionTree model) {
        getInstance().setModelInternal(model);
    }


    private void setModelInternal(ModelDefinitionTree model) {
        this.model = model;
    }

    private void lengthValidate(String object, String item, int length) throws Error, Exception {
        ModelDefinitionTree.LengthValidate lval = model.lengthValidate(object, item);
        if (lval.validate) {
            ExceptionHandlings.addThrow("COD-001", "FIRST EXCEPTION", 3);
        }
    }

    private void setValidateInternal(String object, String item, String type, Object value) throws Exception {
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
            ExceptionHandlings.catchHandlings(e);

        } catch (Error e) {
            ExceptionHandlings.catchHandlings(e);
        }

    }
}