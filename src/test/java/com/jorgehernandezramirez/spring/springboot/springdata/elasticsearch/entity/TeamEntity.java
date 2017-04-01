package com.jorgehernandezramirez.spring.springboot.springdata.elasticsearch.entity;

public class TeamEntity {

    private String name;

    private String sport;

    public TeamEntity(){
        //Para Spring Data
    }

    public TeamEntity(String name, String sport) {
        this.name = name;
        this.sport = sport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "name='" + name + '\'' +
                ", sport='" + sport + '\'' +
                '}';
    }
}
