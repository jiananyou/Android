package com.hziee.exam.pwdmanager;

import java.io.Serializable;

class Password implements Serializable {
    private String title;
    private String pwd;

    Password(String title, String pwd) {
        this.title = title;
        this.pwd = pwd;
    }

    String getTitle() { return title; }

    String getPwd() { return pwd; }
}
