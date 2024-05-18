package com.bookManagement.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookManagement.entity.Book;
import com.bookManagement.repository.BookRepository;

@Service

public class BookService {
	@Autowired
	private BookRepository bookRepo;

	public void save(Book book) {
		bookRepo.save(book);
		
	}
	public List<Book> availableBook(){
		return bookRepo.findAll();
	}
	public void deleteById(int id) {
		bookRepo.deleteById(id);
	}
	
	public Book getBookById(int id) {
		return bookRepo.findById(id).get();
    }
}
