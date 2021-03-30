package com.nazarov.springrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nazarov.springrestapi.model.enums.FileStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "size")
    private Float size;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FileStatus status;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload")
    private Date upload;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified")
    private Date lastModified;

    @ManyToMany(mappedBy = "files", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private List<User> users;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "file")
    @JsonIgnore
    private List<Event> events = new ArrayList<>();

    public void setType(String type){
        if(type == null){
            this.type = "unknown";
        } else this.type = type;
    }

    public void addUser(User user){
        this.users = new ArrayList<>();
        users.add(user);
    }
}