package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "myindex", type = "user")
public class UserEntity {

    @Id
    private String id;

    private String name;

    private String gender;

    private String surname;

    private Integer money;

    private List<String> roles;

    @Field(type = FieldType.Nested)
    private List<TeamEntity> teams;

    public UserEntity(){
        //Para Spring Data
    }

    public UserEntity(String id, String name, String gender, String surname, Integer money, List<String> roles, List<TeamEntity> teams) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.surname = surname;
        this.money = money;
        this.roles = roles;
        this.teams = teams;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<TeamEntity> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamEntity> teams) {
        this.teams = teams;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", surname='" + surname + '\'' +
                ", money=" + money +
                ", roles=" + roles +
                ", teams=" + teams +
                '}';
    }
}
