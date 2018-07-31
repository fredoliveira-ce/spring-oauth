package com.fredoliveira.model;

import javax.validation.constraints.NotNull;

/**
 * Created by fred.oliveira on 27/06/2018.
 */
public class Person {

    @NotNull
    private String name;

    @NotNull
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
