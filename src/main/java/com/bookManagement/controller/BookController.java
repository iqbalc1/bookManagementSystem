package com.bookManagement.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.bookManagement.entity.Book;
import com.bookManagement.entity.Genre;
import com.bookManagement.entity.Publisher;
import com.bookManagement.service.BookService;
import com.bookManagement.service.GenreService;
import com.bookManagement.service.PublisherService;

@Controller
public class BookController {

	@Autowired 
	private BookService service;
	
	@Autowired
	private GenreService genreService;
	
	@Autowired
	private PublisherService publisherService;
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
	
	@GetMapping("register")
	public String registerForm(Model model) {
		 List<Genre> genres = genreService.availableGenre();
		 List<Publisher> publishers = publisherService.availablePublisher();
		model.addAttribute("book", new Book());
		model.addAttribute("genres", genres);
		model.addAttribute("publishers", publishers);
		return "registerBook";
	}
	
	
//	@PostMapping("/save")
//	public String addBook(@ModelAttribute Book book) {
//		service.save(book);
//		return "redirect:/available";
//	}
	
	
	 @PostMapping("/save")
	    public String addBook(@RequestParam(value = "id", required = false) Integer id,
	    		              @RequestParam("title") String title,
	                          @RequestParam("author") String author,
	                          @RequestParam("genreId") Integer genreId,
	                          @RequestParam("publisherId") Integer publisherId) {
	        Genre genre = genreService.findById(genreId);
	        Publisher publisher = publisherService.findById(publisherId);

	        Book book;
	        
	        if (id != null && id>0) {
	        	book = service.getBookById(id);
	        }
	        else {
	            
	            book = new Book();
	        }
	        book.setTitle(title);
	        book.setAuthor(author);
	        book.setGenre(genre);
	        book.setPublisher(publisher);

	        service.save(book);
	        return "redirect:/available";
	    }
	
	
	@RequestMapping("/deleteBook/{id}")
	public String deleteBook(@PathVariable("id")int id) {
		service.deleteById(id);
		return "redirect:/available";
	}
	
	@GetMapping("available")
	public ModelAndView availableBook() {
		List<Book>list=service.availableBook();
		return new ModelAndView("availableBooks","book", list);
	}
	
	@RequestMapping("/editBook/{id}")
	public String editBook(@PathVariable("id") int id,Model model) {
		Book book=service.getBookById(id);
		 List<Genre> genres = genreService.availableGenre();
	     List<Publisher> publishers = publisherService.availablePublisher();
		model.addAttribute("book",book);
		model.addAttribute("genres", genres);
        model.addAttribute("publishers", publishers);
		return "editBook";
}
}









	