package ru.itis.javalab.ttr.annotations.html;

import java.util.ArrayList;
import java.util.List;

public class Form {
    private String action;
    private String method;
    private String enctype;
    private List<Input> inputs;

    public Form(String action, String method,String enctype) {
        this.action = action;
        this.method = method;
        this.enctype = enctype;
        inputs = new ArrayList<>();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Input> getInputs() {
        return inputs;
    }

    public void setInputs(List<Input> inputs) {
        this.inputs = inputs;
    }
}
