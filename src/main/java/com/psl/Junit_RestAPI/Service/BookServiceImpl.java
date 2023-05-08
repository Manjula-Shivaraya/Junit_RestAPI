package com.psl.Junit_RestAPI.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.psl.Junit_RestAPI.Entity.Book;
import com.psl.Junit_RestAPI.Repo.BookRepo;

import jakarta.validation.Valid;
@Service
public class BookServiceImpl implements BookService{
	
	@Autowired
	private BookRepo bookrepo;
	@Override
	public List<Book> List_books() {

		return bookrepo.findAll();
	}

	@Override
	public Book insertbook(@Valid Book book) {
		return bookrepo.save(book);
	}

	@Override
	public Optional<Book> getbyid(int id) {
		return bookrepo.findById(id);
	}

	@Override
	public void delete_book(int id) {
        bookrepo.deleteById(id);
		
	}

	@Override
	public Book updateBook(Book book) {
		return bookrepo.save(book);
	}

}
