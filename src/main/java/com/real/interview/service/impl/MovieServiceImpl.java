package com.real.interview.service.impl;

import com.real.interview.model.Movie;
import com.real.interview.repository.MoviesRepository;
import com.real.interview.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Optional;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MoviesRepository moviesRepository;

    @Override
    public Movie getMovies(Long id) {
        try {
            Optional<Movie> byId = moviesRepository.findById(id);
            return byId.orElseGet(Movie::new);
        } catch (Exception e) {
            log.info("The movie not found in the database {}", id);
            throw e;
        }
    }

    @Override
    public Movie updateMovies(Movie movie) {
        try {
            Optional<Movie> byId = moviesRepository.findById(movie.getId());
            if (byId.isPresent()) {
                return moviesRepository.save(movie);
            }
        } catch (Exception e) {
            log.info("Unexpected error occurred during storing the data : {}", movie);
            throw e;
        }
        return null;
    }

    @Override
    public void deleteMovies(Long id) {
        try {
            Optional<Movie> byId = moviesRepository.findById(id);
            if (byId.isPresent()) {
                moviesRepository.deleteById(id);
            }
        } catch (Exception e) {
            if (e instanceof InputMismatchException) {
                log.info("The requested movie not found in the database {}", id);
                throw e;
            } else {
                log.error("Unexpected error occurred during fetching the record : {}", id);
                throw e;
            }
        }
    }

    @Override
    public Movie addMovies(Movie movie) {
        try {
            return moviesRepository.save(movie);
        } catch (Exception e) {
            log.error("Error occurred during persisting the record : {}", movie);
            throw e;
        }
    }

    @Override
    public Movie getByTitleAndYear(String title, String year) {
        try {
            return moviesRepository.findByTitleAndYear(title, year);
        } catch (Exception e) {
            log.error("Error occurred during finding movie title: {} with release year: {}", title, year);
            throw e;
        }
    }
}
