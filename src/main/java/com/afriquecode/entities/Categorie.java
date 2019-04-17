package com.afriquecode.entities;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Categorie {
    @Id
    private String id;
    private String designation;
    private int nbrVisite;
    @DBRef
    private Collection<Article> articles = new ArrayList<>();

    @Override
    public String toString() {
        return "Categorie{" + "id=" + id + ", "
                + "designation=" + designation + ", "
                + "nbrVisite=" + nbrVisite + '}';
    }
    
}