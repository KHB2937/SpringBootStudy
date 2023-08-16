package io.playdata.bookhive.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data // <- Getter, Setter, ...
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String author;

    private String isbn;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

}
