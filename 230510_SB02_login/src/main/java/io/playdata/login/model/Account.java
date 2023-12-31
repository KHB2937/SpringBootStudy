package io.playdata.login.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Account {
    @Id @GeneratedValue
    private Long id;
    private String loginID;
    private String password;
    private String name;
    private String role;
}
