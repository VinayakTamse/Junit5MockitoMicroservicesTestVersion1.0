package com.myapp.Titan.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.juneau.annotation.Beanc;

import javax.persistence.*;

@Entity
@Table(name="Country")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="capital")
    private String capital;
}
