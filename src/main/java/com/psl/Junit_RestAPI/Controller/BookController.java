package com.psl.Junit_RestAPI.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.Junit_RestAPI.Entity.Book;
import com.psl.Junit_RestAPI.Exception.ResourceNotFound;
import com.psl.Junit_RestAPI.Service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Book")
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/List")
	public List<Book> List_books() {
		return bookService.List_books();
	}

	@PostMapping("/Insert")
	public Book insertbook(@RequestBody @Valid Book book) {
		return bookService.insertbook(book);
	}

	@GetMapping("/getbyid/{id}")
	public ResponseEntity<Book> getbyid(@PathVariable("id") int userId) {

		return bookService.getbyid(userId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

	}

	@DeleteMapping("/Delete/{id}")
	public ResponseEntity<String> delete_book(@PathVariable("id") int id) throws Exception {
		bookService.delete_book(id);
		return new ResponseEntity<String>("Employee deleted successfully!.", HttpStatus.OK);

	}

	@PutMapping("/Updatebyid/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") int id, @RequestBody Book bookdetails)
			throws ResourceNotFound {
		Book book = bookService.getbyid(id).orElseThrow(() -> new ResourceNotFound("User not found :: " + id));
		book.setName(bookdetails.getName());
		book.setSummary(bookdetails.getSummary());
		book.setRating(bookdetails.getRating());
		final Book updatedUser = bookService.updateBook(book);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
}
