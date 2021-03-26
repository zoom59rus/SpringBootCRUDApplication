package com.nazarov.springrestapi.model;

import com.nazarov.springrestapi.model.enums.FileStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upload")
    private Date upload;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modified")
    private Date lastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "file")
    @ToString.Exclude
    private List<Event> events = new ArrayList<>();
}