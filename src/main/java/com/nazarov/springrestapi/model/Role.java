package com.nazarov.springrestapi.model;

import com.nazarov.springrestapi.model.enums.RoleNames;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RoleNames name;

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private List<Account> accounts = new ArrayList<>();

    public void addAccount(Account account){
        this.accounts.add(account);
        account.addRole(this);
    }
}