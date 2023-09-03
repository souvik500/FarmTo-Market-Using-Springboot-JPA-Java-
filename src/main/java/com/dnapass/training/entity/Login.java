package com.dnapass.training.entity;

import jakarta.persistence.Id;

public class Login
{

    private int userid;
    private String password;
    private String role;
    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid) {
        this.userid = userid;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public Login(int userid, String password, String role) {
        super();
        this.userid = userid;
        this.password = password;
        this.role = role;
    }
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }


}
