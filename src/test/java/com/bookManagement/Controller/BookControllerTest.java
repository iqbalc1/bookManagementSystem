package com.bookManagement.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bookManagement.controller.BookController;
import com.bookManagement.entity.Book;
import com.bookManagement.entity.Genre;
import com.bookManagement.entity.Publisher;
import com.bookManagement.service.BookService;
import com.bookManagement.service.GenreService;
import com.bookManagement.service.PublisherService;
//import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BookController.class)
public class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookService bookService;
	
	@MockBean
    private GenreService genreService;

    @MockBean
    private PublisherService publisherService;
	
	//@Autowired
	//private ObjectMapper objectMapper;
	
	private Book book;
	private Genre genre;
	private Publisher publisher;
	
	@BeforeEach
    void setUp() {
        book = new Book();
        book.setId(1);
        book.setTitle("Sample Book");
        book.setAuthor("Sample Author");

        genre = new Genre();
        genre.setId(1);
        genre.setName("Fiction");

        publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("Sample Publisher");
        publisher.setAddress("123 Sample Street");

        book.setGenre(genre);
        book.setPublisher(publisher);
    }
	
	@Test
	void testAddBook() throws Exception {
        when(genreService.findById(1)).thenReturn(genre);
        when(publisherService.findById(1)).thenReturn(publisher);
        when(bookService.getBookById(1)).thenReturn(book);

        mockMvc.perform(post("/save")
                .param("id", "1")
                .param("title", "Sample Book")
                .param("author", "Sample Author")
                .param("genreId", "1")
                .param("publisherId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/available"));

        verify(bookService, times(1)).save(any(Book.class));
    }
	
	 @Test
	    void testAvailableBook() throws Exception {
	        List<Book> books = Arrays.asList(book);

	        when(bookService.availableBook()).thenReturn(books);

	        mockMvc.perform(get("/available"))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("book"))
	                .andExpect(view().name("availableBooks"));
	    }

	    @Test
	    void testEditBook() throws Exception {
	        List<Genre> genres = Arrays.asList(genre);
	        List<Publisher> publishers = Arrays.asList(publisher);

	        when(bookService.getBookById(anyInt())).thenReturn(book);
	        when(genreService.availableGenre()).thenReturn(genres);
	        when(publisherService.availablePublisher()).thenReturn(publishers);

	        mockMvc.perform(get("/editBook/1"))
	                .andExpect(status().isOk())
	                .andExpect(model().attributeExists("book"))
	                .andExpect(model().attributeExists("genres"))
	                .andExpect(model().attributeExists("publishers"))
	                .andExpect(view().name("editBook"));
	    }
	    @Test
	    void testDeleteBook() throws Exception {
	        doNothing().when(bookService).deleteById(1);

	        mockMvc.perform(get("/deleteBook/1"))
	                .andExpect(status().is3xxRedirection())
	                .andExpect(redirectedUrl("/available"));

	        verify(bookService, times(1)).deleteById(1);
	    }
	}
	

