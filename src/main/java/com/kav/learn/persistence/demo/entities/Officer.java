package com.kav.learn.persistence.demo.entities;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Officer {
    private Integer id;
    private Rank rank;
    private String firstName;
    private String lastName;

    public Officer(Rank rank, String firstName, String lastName) {
        this.rank = rank;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Officer(Integer id, Rank rank, String firstName, String lastName) {
        this.id = id;
        this.rank = rank;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}