package com.bookManagement.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookManagement.entity.Book;
import com.bookManagement.entity.Publisher;
import com.bookManagement.repository.BookRepository;
import com.bookManagement.repository.PublisherRepository;

@Service
public class PublisherService {
	@Autowired
    private PublisherRepository publisherRepo;
	
	@Autowired
    private BookRepository bookRepo;

	public List<Publisher> availablePublisher(){
		return publisherRepo.findAll();
	}
	
	public void save(Publisher publisher) {
		   publisherRepo.save(publisher);
    }
	
	public Publisher findById(Integer id) {
        return publisherRepo.findById(id).orElse(null);
    }
	
	
	public void deleteById(int id) {
        Publisher publisher = publisherRepo.findById(id).orElse(null);
        if (publisher != null) {
            List<Book> books = bookRepo.findByPublisherId(id);
            for (Book book : books) {
                book.setPublisher(null);
            }
            bookRepo.saveAll(books);
            publisherRepo.deleteById(id);
        }
  
	}
	
	public Publisher getPublisherById(int id) {
	    return publisherRepo.findById(id).orElse(null);
	}
}



