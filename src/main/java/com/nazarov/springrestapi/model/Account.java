package com.nazarov.springrestapi.model;

import com.nazarov.springrestapi.model.enums.AccountStatus;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @CreatedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @LastModifiedDate
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "updated")
    private Date updated;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "accounts_roles",
            joinColumns = {@JoinColumn(name = "account_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")
            })
    @ToString.Exclude
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role){
        this.roles.add(role);
        role.addAccount(this);
    }
}