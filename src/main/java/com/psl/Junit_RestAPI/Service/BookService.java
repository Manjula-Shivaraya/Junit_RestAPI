package com.psl.Junit_RestAPI.Service;

import java.util.List;
import java.util.Optional;

import com.psl.Junit_RestAPI.Entity.Book;

import jakarta.validation.Valid;

public interface BookService {

	public List<Book> List_books();

	public Book insertbook(@Valid Book book);

	public Optional<Book> getbyid(int id);

	public void delete_book(int id);

	public Book updateBook(Book book);

}
