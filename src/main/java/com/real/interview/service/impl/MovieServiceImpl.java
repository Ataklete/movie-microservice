package com.real.interview.service.impl;

import com.real.interview.model.Movie;
import com.real.interview.repository.MoviesRepository;
import com.real.interview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MoviesRepository moviesRepository;

    @Override
    public Movie getMovies(Long id) {
        Optional<Movie> byId = moviesRepository.findById(id);
        return byId.orElse(null);
    }

    @Override
    public Movie updateMovies(Movie movie) {
        Optional<Movie> byId = moviesRepository.findById(movie.getId());
        if (byId.isPresent()) {
            return moviesRepository.save(movie);
        }
        return null;
    }

    @Override
    public void deleteMovies(Long id) {
        Optional<Movie> byId = moviesRepository.findById(id);
        if (byId.isPresent()) {
            moviesRepository.deleteById(id);
        }
    }

    @Override
    public Movie addMovies(Movie movie) {
        return moviesRepository.save(movie);
    }

    @Override
    public Movie getByTitleAndYear(String title, String year) {
        return moviesRepository.findByTitleAndYear(title, year);
    }
}
