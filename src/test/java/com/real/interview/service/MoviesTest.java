package com.real.interview.service;

import com.real.interview.model.Movie;
import com.real.interview.repository.MoviesRepository;
import com.real.interview.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class MoviesTest {

    @InjectMocks
    private MovieServiceImpl movieService;

    @Mock
    private MoviesRepository moviesRepository;

    @Test
    void insert_movie_when_called_add_method() {
        Movie movie = new Movie(1L, "test", "2025");
        Mockito.when(moviesRepository.save(movie)).thenReturn(movie);
        Movie mo = movieService.addMovies(movie);
        Assertions.assertEquals(1L, mo.getId());
    }

    @Test
    void get_movie_when_called_get_title_and_year_method() {
        Movie movie = new Movie(1L, "test", "2025");
        Mockito.when(moviesRepository.findByTitleAndYear("test", "2025")).thenReturn(movie);
        Movie mo = movieService.getByTitleAndYear("test", "2025");
        Assertions.assertEquals("test", mo.getTitle());
        Assertions.assertEquals("2025", mo.getReleaseYear());
    }

    @Test
    void get_movie_when_called_get_by_id_method() {
        Movie movie = new Movie(1L, "test", "2025");
        Mockito.when(moviesRepository.findById(1L)).thenReturn(Optional.of(movie));
        Movie mo = movieService.getMovies(1L);
        Assertions.assertEquals("test", mo.getTitle());
        Assertions.assertEquals("2025", mo.getReleaseYear());
    }

    @Test
    void get_movie_when_called_delete_method() {
        Movie movie = new Movie(1L, "test", "2025");
        Mockito.when(moviesRepository.existsById(1L)).thenReturn(Boolean.TRUE);
        Mockito.doNothing().when(moviesRepository).deleteById(1L);
        movieService.deleteMovies(1L);
        Mockito.verify(moviesRepository, Mockito.atLeastOnce()).deleteById(1L);
    }
}
