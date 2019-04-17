package com.afriquecode;

import java.util.ArrayList;

import com.afriquecode.entities.Article;
import com.afriquecode.entities.Categorie;
import com.afriquecode.repository.ArticleRepository;
import com.afriquecode.repository.CategorieRepository;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AfriquecodeApplication implements CommandLineRunner {

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    ArticleRepository articleRepository;

    public static void main(String[] args) {
        SpringApplication.run(AfriquecodeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
                // =================== categorie =====================
        categorieRepository.deleteAll();
        Categorie cat2 = new Categorie("cat001", "JAVA WEB (JEE, EJB, JSP)", 200, new ArrayList<Article>());
        Categorie cat1 = new Categorie("cat002", "JAVA WEB (SPRING)", 152, new ArrayList<Article>());
        Categorie cat3 = new Categorie("cat003", "JAVA ANDROID", 200, new ArrayList<Article>());

        Stream.of(cat1, cat2, cat3).forEach(cat -> {
        categorieRepository.save(cat);
        });
        categorieRepository.findAll().forEach(System.out::println);
        
        // ================== article =========================
        articleRepository.deleteAll();
        Categorie c1 = categorieRepository.findById("cat002").get();
        Categorie c2 = categorieRepository.findById("cat003").get();
        
        Article art1 = new Article(null, "Réaliser une application JEE part 1", "Architecture en couche, séparation des responsabilités, ID, CI", LocalDate.now(), 550, c1);
        Article art2 = new Article(null, "Réaliser une application JEE part 2", "Architecture en couche, séparation des responsabilités, ID, CI", LocalDate.now(), 550, c2);
        
        Stream.of(art1, art2).forEach( art -> {
            articleRepository.save(art);
        });        
        c1.getArticles().add(art1);
        c2.getArticles().add(art2);
        articleRepository.findAll().forEach(System.out::println);
        
        System.out.println("traitement terminé");
    }
}
