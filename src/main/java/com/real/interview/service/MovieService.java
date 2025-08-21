package com.real.interview.service;

import com.real.interview.model.Movie;

public interface MovieService {
    Movie getMovies(Long id);
    Movie updateMovies(Movie movie);
    String deleteMovies(Long id);
    Movie addMovies(Movie movie);
    Movie getByTitleAndYear(String title, String releaseYear);


}
