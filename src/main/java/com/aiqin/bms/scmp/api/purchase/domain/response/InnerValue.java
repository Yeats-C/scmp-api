package com.aiqin.bms.scmp.api.purchase.domain.response;


import lombok.Data;

@Data
public class InnerValue {
    private String name;

    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
