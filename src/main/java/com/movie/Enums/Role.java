package com.movie.Enums;

public enum  Role {
    User("User"),
    CinemaMng("CinemaMng"),
    SystemMng("SystemMng"),
    Vistor("Vistor")
    ;

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
