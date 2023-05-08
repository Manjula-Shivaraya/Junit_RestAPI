package com.psl.Junit_RestAPI;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Empty;
import com.psl.Junit_RestAPI.Entity.Book;
import com.psl.Junit_RestAPI.Service.BookService;

@WebMvcTest // It is used to load the controller class.
@DisplayName("To test the REST API's")
class JunitRestApiApplicationTests {

	@MockBean // It is used to create an instance of this class and to provide it ro the
				// Spring application context.
	BookService bookService;

	@Autowired
	MockMvc mockmvc;

	@Autowired
	ObjectMapper objectMapper; // To read and write the JSON

	@Nested
	@DisplayName("Test Cases for Data retrieval operation")
	class Retrieval_of_Data {

		// JUnit test for Get All employees REST API
		@Test
		@DisplayName("JUnit test to retrive List of Books REST API")
		public void TestList_Books() throws Exception {
			// given - precondition or setup
			List<Book> ListOfBooks = new ArrayList<>();
			Book book1 = new Book(1, "Novel", "Summary of it", 5);
			Book book2 = new Book(2, "Novel2", "Summary of it2", 4);
			ListOfBooks.add(book2);
			ListOfBooks.add(book1);
			given((bookService.List_books())).willReturn(ListOfBooks);

			// when - action or behaviour that we are going test
			ResultActions response = mockmvc.perform(get("/Book/List"));

			// then - verify the result or output using assert statements
			response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.size()", is(ListOfBooks.size())));

		}

		// positive scenario - valid employee id
		@Test
		@DisplayName("JUnit test to GET Book by id REST API : +ve")
		public void TestGetby_id_Pos() throws Exception {
			// SETUp
			int bookId = 1;
			Book book = new Book(1, "Novel", "Summary of it", 5);
			given(bookService.getbyid(bookId)).willReturn(Optional.of(book));
			ResultActions response = mockmvc.perform(get("/Book/getbyid/{id}", bookId));
			response.andExpect(status().isOk()).andExpect(jsonPath("$.name", is(book.getName())))
					.andExpect(jsonPath("$.summary", is(book.getSummary())))
					.andExpect(jsonPath("$.rating", is(book.getRating())));
		}

		// negative scenario - valid employee id
		@DisplayName("JUnit test to GET Book by id REST API : -ve")
		@Test
		public void TestGeyby_id_Neg() throws Exception {
			// SetUp
			int Book_id = 9;
			given(bookService.getbyid(Book_id)).willReturn(Optional.empty());
			// When
			ResultActions response = mockmvc.perform(get("http://localhost:8080/Book/getbyid/{id}", Book_id));

			// then
			response.andExpect(status().isNotFound());

		}
	}

	@DisplayName("Test Cases for Book Creation operation")
	@Nested
	class Inserting_Data {

		@DisplayName("Junit Test to Insert a Book")
		@Test
		public void TestInsert_Book() throws Exception {
			Book book = new Book(1, "Novel", "Summary of it", 5);

			// given(bookService.insertbook(book)).willReturn(book);
			given(bookService.insertbook(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(0));

			// when
			ResultActions response = mockmvc.perform(post("/Book/Insert").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(book)));

			// then
			response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.name", is(book.getName())))
					.andExpect(jsonPath("$.summary", is(book.getSummary())))
					.andExpect(jsonPath("$.rating", is(book.getRating())));

		}
	}

	@DisplayName("Test Cases for Book Delete operation")
	@Nested
	class Deleting_Data {

		@DisplayName("Junit Test to Delete a Book")
		@Test
		public void TestDelete_Book() throws Exception {
			int bookid = 2;
			willDoNothing().given(bookService).delete_book(bookid);
			ResultActions response = mockmvc.perform(delete("/Book/Delete/{id}", bookid));
			response.andExpect(status().isOk()).andDo(print());
		}
	}

	@DisplayName("Test Cases for Updating the Book Entity")
	@Nested
	class Updating_Data {

		@DisplayName("Junit Test to Update a Book : +ve")
		@Test
		public void TestUpdate_Book_pos() throws Exception {
			int id = 1;
			Book saved_data = new Book(1, "Novel", "Summary of it", 5);
			Book updated_data = new Book(1, "Novel22", "Summary of it222", 3);
			given(bookService.getbyid(id)).willReturn(Optional.of(saved_data));
			given(bookService.updateBook(any(Book.class))).willAnswer((invocation) -> invocation.getArgument(0));

			ResultActions response = mockmvc.perform(put("/Book/Updatebyid/{id}", id)
					.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updated_data)));

			response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.name", is(updated_data.getName())))
					.andExpect(jsonPath("$.summary", is(updated_data.getSummary())))
					.andExpect(jsonPath("$.rating", is(updated_data.getRating())));
		}

		@DisplayName("Junit Test to Update a Book : -ve")
		@Test
		public void TestUpdate_Book_neg() throws Exception {
			int id = 1;
			Book saved_data = new Book(1, "Novel", "Summary of it", 5);
			Book updated_data = new Book(1, "Novel22", "Summary of it222", 3);
			given(bookService.getbyid(id)).willReturn(Optional.empty());
			given(bookService.updateBook(any(Book.class))).willAnswer((x) -> x.getArgument(0));
			ResultActions response = mockmvc.perform(put("/Book/Updatebyid/{id}",id).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updated_data)));
			response.andExpect(status().isNotFound()).andDo(print());
		}
	}
}
