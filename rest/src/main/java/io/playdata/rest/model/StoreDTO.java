package io.playdata.rest.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "store") //jpa를 통한 테이블 생성 시 몇칭 지정
public class StoreDTO {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String category;
    int price;
}
