package com.example.adoptionproject.entities;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnimal;

    private String nom;
    private int age;
    private boolean sterilise;

    @Enumerated(EnumType.STRING)
    private Espece espece;
}