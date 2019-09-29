package com.linix_lab.snapbazaar.Model;

public class Feedback {
    private String fphone;
    private String fproduct;
    private String fback;

    public Feedback(){

    }

    public Feedback(String fphone, String fproduct, String fback) {
        this.fphone = fphone;
        this.fproduct = fproduct;
        this.fback = fback;
    }

    public String getFphone() {
        return fphone;
    }

    public void setFphone(String fphone) {
        this.fphone = fphone;
    }

    public String getFproduct() {
        return fproduct;
    }

    public void setFproduct(String fproduct) {
        this.fproduct = fproduct;
    }

    public String getFback() {
        return fback;
    }

    public void setFback(String fback) {
        this.fback = fback;
    }
}
