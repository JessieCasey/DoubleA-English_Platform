package com.doubleA.platform.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tuition")
@Data
public class Tuition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price")
    private Double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

}
