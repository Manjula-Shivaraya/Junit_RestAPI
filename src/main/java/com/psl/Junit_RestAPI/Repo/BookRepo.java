package com.psl.Junit_RestAPI.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.Junit_RestAPI.Entity.Book;
@Repository
public interface BookRepo extends JpaRepository<Book, Integer>{

}
