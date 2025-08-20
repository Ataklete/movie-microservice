package com.real.interview.repository;

import com.real.interview.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MoviesRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m WHERE m.title = :title AND m.releaseYear = :year")
    Movie findByTitleAndYear(@Param("title") String title, @Param("year") String year);
}
