package com.real.interview.service.impl;

import com.real.interview.exception.MovieNotFoundException;
import com.real.interview.model.Movie;
import com.real.interview.repository.MoviesRepository;
import com.real.interview.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MoviesRepository moviesRepository;

    @Override
    public Movie getMovies(Long id) {
        try {
            return moviesRepository.findById(id).orElseThrow(()->new MovieNotFoundException("data not found for: " + id));
        } catch (MovieNotFoundException e) {
            log.info("The movie with id# {} not found in the database", id);
            throw new MovieNotFoundException(e.getMessage());
        }
    }

    @Override
    public Movie updateMovies(Movie movie) {
        try {
            Movie movieById = moviesRepository.findById(movie.getId()).orElseGet(Movie::new);
            movieById.setId(movieById.getId());
            movieById.setTitle(movie.getTitle());
            movieById.setReleaseYear(movie.getReleaseYear());
                return moviesRepository.save(movieById);
        } catch (Exception e) {
            log.info("Unexpected error occurred during storing the data : {}", movie);
            throw new MovieNotFoundException(e.getMessage());
        }
    }

    @Override
    public String deleteMovies(Long id) {
        try {
            if (moviesRepository.existsById(id)) {
                moviesRepository.deleteById(id);
                return "one record deleted successfully with id: " + id;
            }else {
                return "Data not found with id: " + id;
            }
        } catch (Exception e) {
            if (e instanceof MovieNotFoundException) {
                log.info("The requested movie not found in the database {}", id);
                throw new MovieNotFoundException(e.getMessage());
            } else {
                log.error("Unexpected error occurred during fetching the record : {}", id);
                throw new RuntimeException("Unexpected error");
            }
        }
    }

    @Override
    public Movie addMovies(Movie movie) {
        try {
            return moviesRepository.save(movie);
        } catch (Exception e) {
            log.error("Error occurred during persisting the record : {}", movie);
            throw new MovieNotFoundException(e.getMessage());
        }
    }

    @Override
    public Movie getByTitleAndYear(String title, String releaseYear) {
        try {
            return moviesRepository.findByTitleAndYear(title, releaseYear);
        } catch (NullPointerException e) {
            log.error("Error occurred during finding movie title: {} with release year: {}", title, releaseYear);
            throw new MovieNotFoundException(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred");
            throw new RuntimeException("Unexpected error");
        }
    }
}
