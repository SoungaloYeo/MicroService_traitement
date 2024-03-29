package com.afriquecode.repository;

import com.afriquecode.entities.Article;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends MongoRepository<Article, String> {
    
}