package com.videoclub.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.videoclub.movie.Movie;

@Repository
@Configurable
public interface MovieRepository extends JpaRepository<Movie, Integer>{
	List<Movie> findByNaslov(String naslov);
	List<Movie> findByZanr(String zanr);
	List<Movie> findByInventarskiBroj(Integer vrednost);
	List<Movie> findByJezik(String jezik);
	List<Movie> findByGodinaSnimanja(String godina_snimanja);
	List<Movie> findByMpaRating(Integer mpa_rating);

	// Trazi filmove koji nisu pozajmljeni
	List<Movie> findByInventarskiBrojNotIn(List<Integer> inventarskiBroj);
}
