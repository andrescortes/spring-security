package com.tutorial.crud.security.entity;

import com.tutorial.crud.security.enums.NameRole;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private NameRole nameRole;

    public Role() {

    }

    public Role(NameRole nameRole) {
        this.nameRole = nameRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NameRole getNameRole() {
        return nameRole;
    }

    public void setNameRole(NameRole nameRole) {
        this.nameRole = nameRole;
    }
}
