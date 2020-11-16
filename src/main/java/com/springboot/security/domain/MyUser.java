package com.springboot.security.domain;

/**
 * @Author: ChenJunNan
 * @Description:
 * @Date: create in 2020/11/16 10:29
 */
public class MyUser {
    private Integer id;

    private String name;

    private String password;

    private Role role;

    public MyUser(Integer id, String name, String password, Role role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MyUser() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
