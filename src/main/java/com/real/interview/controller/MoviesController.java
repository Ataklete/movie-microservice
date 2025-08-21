package com.real.interview.controller;

import com.real.interview.model.Movie;
import com.real.interview.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getMovies(@PathVariable Long id) {
        Movie movie = movieService.getMovies(id);
        return ResponseEntity.status(HttpStatus.OK).body(movie);
    }

    @GetMapping("/{title}/{year}")
    public ResponseEntity<?> getMoviesByTitleAndYear(@Validated @PathVariable String title, @PathVariable String year) {
        Movie movie = movieService.getByTitleAndYear(title, year);
        return ResponseEntity.status(HttpStatus.OK).body(movie);
    }

    @PostMapping
    public ResponseEntity<?> addMovie(@Validated @RequestBody Movie movie, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
       return ResponseEntity.ok(movieService.addMovies(movie));
    }

    @PutMapping
    public ResponseEntity<?> updateMovie(@Validated @RequestBody Movie movies, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        return ResponseEntity.status(HttpStatus.OK).body(movieService.updateMovies(movies));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovies(@PathVariable  Long id) {
        String deletedMovies = movieService.deleteMovies(id);
        return ResponseEntity.status(HttpStatus.OK).body(deletedMovies);
    }
}
