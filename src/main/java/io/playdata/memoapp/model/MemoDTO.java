package io.playdata.memoapp.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "memo")
public class MemoDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String text;
    Long userId; // MemoUser의 ID
}
