package com.real.interview.service;

import com.real.interview.model.Movie;

public interface MovieService {
    Movie getMovies(Long id);
    Movie updateMovies(Movie movie);
    void deleteMovies(Long id);
    Movie addMovies(Movie movie);
    Movie getByTitleAndYear(String title, String year);


}
