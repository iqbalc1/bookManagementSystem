package com.bookManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookManagement.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
	
}
