package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "myindex", type = "user")
public class UserEntity {

    @Id
    private String id;

    private String userId;

    private String bankId;

    private Integer numberproducts;

    public UserEntity(){
        //Para Spring Data
    }

    public UserEntity(String id, String userId, String bankId, Integer numberproducts) {
        this.id = id;
        this.userId = userId;
        this.bankId = bankId;
        this.numberproducts = numberproducts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Integer getNumberproducts() {
        return numberproducts;
    }

    public void setNumberproducts(Integer numberproducts) {
        this.numberproducts = numberproducts;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", bankId='" + bankId + '\'' +
                ", numberproducts=" + numberproducts +
                '}';
    }
}
