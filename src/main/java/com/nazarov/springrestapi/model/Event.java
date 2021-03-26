package com.nazarov.springrestapi.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    @ToString.Exclude
    private File file;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    private Date created;

    @Column(name = "event")
    private String event;
}