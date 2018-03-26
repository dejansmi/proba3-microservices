package com.dejans.proba3microservices;

import java.util.ArrayList;
import java.util.List;

public class ExceptionHandlings {

    private List<StackTraceElement[]> sTE = new ArrayList<StackTraceElement[]>();
    private List<String> exceptions = new ArrayList<String>();
    private List<Integer> forUser = new ArrayList<Integer>();
    
    private boolean firstHandling = false;

    private static ExceptionHandlings instance = null;

    // this is Singleton class
    public static ExceptionHandlings getInstance() {
        if (instance == null) {
            instance = new ExceptionHandlings();
        }
        return instance;
    }

    protected ExceptionHandlings() {
    }

    // forUser -
    //   0 (or null) - don't show error to Client (end customer) or User
    //               - insted this message show Internal error, try again
    //   1 - show message to Client but not to User
    //   2 - show message to User byt not Client
    //   3 - show message to User and Client 
    public void addThrow(String code, String message, int forUser) throws Exception {
        if (forUser < 0 || forUser > 3) forUser = 0;
        String fu = String.valueOf(forUser);
        throw new Exception(code + ":" + message + "$$$" + fu );
    }

    public void addThrowNoBreak(String code, String message) {

    }

    //TODO: SR: ako postoji Exceptions koji nije izazvao prekid sada ce biti izazvan prekid
    public void checkExceptionsThrow() {

    }

    //TODO: SR: true - ako postoje greske koje su se desile a nisu prekinule
    public boolean ifExceptions() {
        return false;
    }

    public void catchHandlings(Exception e) throws Exception {
        System.out.println("CATCH MYEXCEPTIONS HANDLINGS");
        sTE.add(e.getStackTrace());
        if (!firstHandling) {
            exceptions.add(e.toString().replace("java.lang.", ""));
            firstHandling = true;
        }
        throw e;
    }

    public void catchHandlingsEnd(Exception e) throws Error {
        System.out.println("CATCH ERROR HANDLINGS END");
        sTE.add(e.getStackTrace());
        if (!firstHandling) {
            exceptions.add(e.toString().replace("java.lang.", ""));
            firstHandling = true;
        }
        System.out.println("EXCEPTION STACK:");
        for (String ex : exceptions) {
            System.out.println(ex);
        }
        System.out.println();
        for (StackTraceElement[] ste : sTE) {
            for (StackTraceElement steOne : ste) {
                System.out.println(steOne.toString());
            }
        }
        System.out.println();

    }

    public String catchHandlingsHTTP(Exception e) throws Error {
        String tempString = new String();
        try {
            System.out.println("CATCH ERROR HANDLINGS END");
            sTE.add(e.getStackTrace());
            if (!firstHandling) {
                exceptions.add(e.toString().replace("java.lang.", ""));
                firstHandling = true;
            }
            System.out.println("EXCEPTION STACK:");
            for (String ex : exceptions) {
                String temp = ex;
                if (temp.contains("$$$")) {
                    // it's user defined (custom) exception by this system 
                    // TODO: SR: Treba da se definise prikaz greske Client ili User u zavisnosti
                    // da li je to Client ili User (citati konfiguraciju)
                    if (!temp.substring(temp.length()-1,temp.length()).equals("0")) {
                        temp = temp.substring(0,temp.length()-4);
                    } else {
                        // SR: Ukoliko nije definisano da se prikazuje originalna poruka 
                        // prikazuje se Ilegal error poruka
                        temp = "Ilegal error, try again";
                    }
                }
                System.out.println(ex);
                System.out.println(temp);
                tempString += temp + "<br/>";
            }
            System.out.println();
            for (StackTraceElement[] ste : sTE) {
                for (StackTraceElement steOne : ste) {
                    System.out.println(steOne.toString());
                }
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("SUPERMEN");

            System.out.println();
            System.out.println();
        } finally {
            if (tempString == null || tempString == "") {
                tempString = "Ilegal error, try again<br/>";
            }
            return tempString;
        }
    }

    public void catchHandlings(Error e) throws Error {
        System.out.println("CATCH ERROR HANDLINGS");
        sTE.add(e.getStackTrace());
        if (!firstHandling) {
            exceptions.add(e.toString().replace("java.lang.", ""));
            firstHandling = true;
        }
        throw e;

    }

}