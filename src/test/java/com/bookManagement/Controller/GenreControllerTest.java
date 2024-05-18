package com.bookManagement.Controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.bookManagement.controller.GenreController;
import com.bookManagement.entity.Genre;
import com.bookManagement.service.GenreService;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    private Genre genre;
    
    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setId(1);
        genre.setName("Fiction");
    }

    @Test
    void testAddGenrePage() throws Exception {
        mockMvc.perform(get("/addGenre"))
                .andExpect(status().isOk())
                .andExpect(view().name("addGenre"));
    }

    @Test
    void testAvailableGenre() throws Exception {
        List<Genre> genres = Arrays.asList(genre);

        when(genreService.availableGenre()).thenReturn(genres);

        mockMvc.perform(get("/availableGenre"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("Genre"))
                .andExpect(view().name("availableGenre"));
    }

    @Test
    void testSaveGenre() throws Exception {
        mockMvc.perform(post("/saveGenre")
                .flashAttr("genre", genre))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/availableGenre"));

        verify(genreService, times(1)).save(any(Genre.class));
    }
}
	
