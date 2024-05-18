package com.bookManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookManagement.entity.Publisher;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {

	
}
