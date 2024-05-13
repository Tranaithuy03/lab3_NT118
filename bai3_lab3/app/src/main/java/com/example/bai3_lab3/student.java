package com.example.bai3_lab3;

public class student {
    private String mssv;
    private String name;
    private String _class;

    public student(String mssv, String name, String _class) {
        this.mssv = mssv;
        this.name = name;
        this._class = _class;
    }

    public String getMssv() {
        return mssv;
    }

    public void setMssv(String mssv) {
        this.mssv = mssv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }
}
