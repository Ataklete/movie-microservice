package com.real.interview.controller;


import com.real.interview.model.Movie;
import com.real.interview.repository.MoviesRepository;
import com.real.interview.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoviesController.class)
class MoviesControllerTest {

    @Autowired
    private MockMvc mvc;

    @InjectMocks
    private MoviesController moviesController;

    @MockitoBean
    private MovieServiceImpl movieService;

    @MockitoBean
    private MoviesRepository moviesRepository;


    @Test
    void getMovies() throws Exception {
        Mockito.when(movieService.getMovies(1L)).thenReturn(new Movie(1L,"test", "2025"));
        int status = mvc.perform(MockMvcRequestBuilders.get("/movie/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }

    @Test
    void getMoviesByTitleAndYear() throws Exception {
        Mockito.when(movieService.getByTitleAndYear("test", "2025")).thenReturn(new Movie(1L,"test", "2025"));
        mvc.perform(MockMvcRequestBuilders.get("/movie/{title}/{releaseYear}", "test", "2025"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title ").value("test"))
                .andExpect(jsonPath("$.releaseYear ").value("2025"));
    }

    @Test
    void addMovie() throws Exception {
        String s = """
                {
                    "title": "new Movie",
                    "releaseYear": "2025"
                }
                """;
        Movie newMovie = new Movie(1L, "new Movie", "2025");
        Mockito.when(movieService.addMovies(newMovie)).thenReturn(newMovie);
        int resultActions = mvc.perform(MockMvcRequestBuilders.post("/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
        ).andReturn().getResponse().getStatus();
        Assertions.assertEquals(200, resultActions);

    }

    @Test
    void updateMovie() throws Exception {
        String s = """
                {
                    "id": 1,
                    "title": "new Movie",
                    "releaseYear": "2025"
                }
                """;
        Movie newMovie = new Movie(1L, "new Movie", "2025");
        Mockito.when(movieService.updateMovies(newMovie)).thenReturn(newMovie);
        mvc.perform(MockMvcRequestBuilders.put("/movie")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title ").value("new Movie"))
                .andExpect(jsonPath("$.releaseYear ").value("2025"));
    }

    @Test
    void deleteMovies() throws Exception {
        Mockito.when(moviesRepository.findById(1L)).thenReturn(Optional.of(new Movie(1L, "test", "2025")));
        Mockito.doNothing().when(movieService).deleteMovies(1L);
        int status = mvc.perform(MockMvcRequestBuilders.delete("/movie/{id}", 1L).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getStatus();
        Assertions.assertEquals(200, status);
    }
}