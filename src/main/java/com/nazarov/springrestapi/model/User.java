package com.nazarov.springrestapi.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    @ToString.Exclude
    private Account account;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<File> files;
}