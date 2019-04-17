package com.afriquecode.entities;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    private String id;
    private String theme;
    private String description;
    private LocalDate datePublication;
    private int nbrDeVue;
    @DBRef
    private Categorie categorie;
}