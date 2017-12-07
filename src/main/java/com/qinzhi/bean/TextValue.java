package com.qinzhi.bean;

/**
 *
 */
public class TextValue {

    private java.lang.String text;
    private java.lang.String value;

    public TextValue(String value,String text){
        this.text = text;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
