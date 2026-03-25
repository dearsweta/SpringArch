package com.spring.internal_working.internal_work.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@ToString
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Addresses {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private  String state;

    @Column(name = "zip")
    private String zip;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    User user_obj;
}



