package com.info.prescription.viewModel.api;

import lombok.Data;

@Data
public class MinConceptItem {

    private String rxcui;
    private String name;
    private String tty;

    public String getRxcui() {
        return rxcui;
    }

    public void setRxcui(String rxcui) {
        this.rxcui = rxcui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTty() {
        return tty;
    }

    public void setTty(String tty) {
        this.tty = tty;
    }
}
