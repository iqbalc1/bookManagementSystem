package com.bookManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//import com.bookManagement.entity.Book;
//import com.bookManagement.entity.Genre;
import com.bookManagement.entity.Publisher;
import com.bookManagement.service.PublisherService;

@Controller
public class PublisherController  {
	@Autowired
    private PublisherService publisherService;
	
    @GetMapping("/addPublisher")
    public String addPublisher() {
        return "addPublisher";
    }

    @GetMapping("/availablePublisher")
	public ModelAndView availablePublisher() {
		List<Publisher>list=publisherService.availablePublisher();
		return new ModelAndView("availablePublisher","Publisher", list);
}
    @PostMapping("/savePublisher")
    public String addPublisher(@ModelAttribute Publisher publisher) {
        publisherService.save(publisher);
        return "redirect:/availablePublisher";
    } 
  
    @RequestMapping("/deletePublisher/{id}")
	public String deletePublisher(@PathVariable("id")int id) {
		publisherService.deleteById(id);
		return "redirect:/availablePublisher";
	}
    
    @RequestMapping("/editPublisher/{id}")
    public String editPublisher(@PathVariable("id") int id, Model model) {
        Publisher publisher = publisherService.getPublisherById(id); // Corrected method invocation

        model.addAttribute("publisher", publisher);

        return "editPublisher"; 
    }
}


