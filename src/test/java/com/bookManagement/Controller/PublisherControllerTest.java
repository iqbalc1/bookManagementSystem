package com.bookManagement.Controller;

import static org.mockito.ArgumentMatchers.any;
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

import com.bookManagement.controller.PublisherController;
import com.bookManagement.entity.Publisher;
import com.bookManagement.service.PublisherService;

@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublisherService publisherService;

    private Publisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new Publisher();
        publisher.setId(1);
        publisher.setName("Sample Publisher");
        publisher.setAddress("123 Sample Street");
    }
    @Test
    void testSavePublisher() throws Exception {
        mockMvc.perform(post("/savePublisher")
                .flashAttr("publisher", publisher))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/availablePublisher"));

        verify(publisherService, times(1)).save(any(Publisher.class));
    }
    
    @Test
    void testEditPublisher() throws Exception {
        when(publisherService.getPublisherById(1)).thenReturn(publisher);

        mockMvc.perform(get("/editPublisher/1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("publisher"))
                .andExpect(view().name("editPublisher"));
    }
    
    @Test
    void testAvailablePublisher() throws Exception {
        List<Publisher> publishers = Arrays.asList(publisher);

        when(publisherService.availablePublisher()).thenReturn(publishers);

        mockMvc.perform(get("/availablePublisher"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("Publisher"))
                .andExpect(view().name("availablePublisher"));
    }
    
    @Test
    void testDeletePublisher() throws Exception {
        doNothing().when(publisherService).deleteById(1);

        mockMvc.perform(get("/deletePublisher/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/availablePublisher"));

        verify(publisherService, times(1)).deleteById(1);
    }
    
    
}

