package com.springboot.security.domain;

/**
 * @Author: ChenJunNan
 * @Description:
 * @Date: create in 2020/11/16 10:31
 */
public class Role {
    private Integer id;

    private String name;

    public Role() {
    }

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
