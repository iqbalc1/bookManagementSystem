package com.bookManagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.bookManagement.entity.Genre;
import com.bookManagement.service.GenreService;

@Controller
public class GenreController {

	@Autowired
    private GenreService genreService;

    @GetMapping("/addGenre")
    public String addGenre() {
        return "addGenre";
    }

    
    @GetMapping("/availableGenre")
	public ModelAndView availableGenre() {
		List<Genre>list=genreService.availableGenre();
		return new ModelAndView("availableGenre","Genre", list);
}
    @PostMapping("/saveGenre")
    public String addGenre(@ModelAttribute Genre genre) {
        genreService.save(genre);
        return "redirect:/availableGenre";
    } 


}


