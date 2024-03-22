package com.videoclub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.videoclub.movie.Movie;
import com.videoclub.repository.MovieRepository;

@Service
@Configurable
public class MovieService {
	@Autowired
	 private MovieRepository repo;
	
		public List<Movie> listAllMovies(){
			return (List<Movie>) repo.findAll();
		}
	
		public void save(Movie movie) {
			repo.save(movie);
		}
		
		public Movie editMovie(Integer inventarskiBroj){
			Optional<Movie> result = repo.findById(inventarskiBroj);
			return result.get();			
		}

		public void deleteMovie(Integer inventarskiBroj) {

			repo.deleteById(inventarskiBroj);
			
		}
		
		public List<Movie> findMoviesByNaslov(String keyword){
			return repo.findByNaslov(keyword);
		}
		public List<Movie> findMoviesByZanr(String keyword){
			return repo.findByZanr(keyword);
		}
		public List<Movie> findMoviesByInventarskomBroju(Integer vrednost){
			return repo.findByInventarskiBroj(vrednost);
		}
		public List<Movie> findMoviesByJezik(String keyword){
			return repo.findByJezik(keyword);
		}
		public List<Movie> findMoviesByGodinaSnimanja(String keyword){
			return repo.findByGodinaSnimanja(keyword);
		}
		public List<Movie> findMoviesByMpaRating(String keyword){
			return repo.findByMpaRating(keyword);
		}
	
}
