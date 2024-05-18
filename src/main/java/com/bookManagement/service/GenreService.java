package com.bookManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookManagement.entity.Genre;
import com.bookManagement.repository.GenreRepository;
@Service
public class GenreService {
	
	@Autowired
    private GenreRepository genreRepo;
     
    public List<Genre> availableGenre(){
		return genreRepo.findAll();
	}
	public void save(Genre genre) {
		   genreRepo.save(genre);
    }
	 public Genre findById(Integer id) {
	        return genreRepo.findById(id).orElse(null);
	    }
	 
		   
	}


