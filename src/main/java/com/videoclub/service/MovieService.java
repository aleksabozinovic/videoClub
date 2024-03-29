package com.videoclub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.videoclub.member.Member;
import com.videoclub.movie.Movie;
import com.videoclub.repository.MovieRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Service
@Configurable
public class MovieService {
	@Autowired private MovieRepository repo;
	@Autowired private EntityManager entitiyManager;

	
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
		
		public List<Movie> findMoviesByNaslov(String naslov){

			CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
			CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
			Root<Movie> movie = cq.from(Movie.class);
			cq.select(movie).where(cb.like(movie.get("naslov"), naslov  + "%"));
			return entitiyManager.createQuery(cq).getResultList();
		}
		public List<Movie> findMoviesByZanr(String zanr){
			CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
			CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
			Root<Movie> movie = cq.from(Movie.class);
			cq.select(movie).where(cb.like(movie.get("zanr"), zanr  + "%"));
			return entitiyManager.createQuery(cq).getResultList();
			
		}
		public List<Movie> findMoviesByInventarskomBroju(Integer inventarskiBroj){
			 String inventarskiBrojString = String.valueOf(inventarskiBroj);
			    return repo.findByInventarskiBrojLike(inventarskiBrojString);		
				}
		public List<Movie> findMoviesByJezik(String jezik){
			CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
			CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
			Root<Movie> movie = cq.from(Movie.class);
			cq.select(movie).where(cb.like(movie.get("jezik"), jezik  + "%"));
			return entitiyManager.createQuery(cq).getResultList();
		}
		
		public List<Movie> findMoviesByGodinaSnimanja(String godinaSnimanja){
			CriteriaBuilder cb = entitiyManager.getCriteriaBuilder();
			CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
			Root<Movie> movie = cq.from(Movie.class);
			cq.select(movie).where(cb.like(movie.get("godinaSnimanja"), godinaSnimanja  + "%"));
			return entitiyManager.createQuery(cq).getResultList();		
			
		}
		
		public List<Movie> findMoviesByMpaRating(Integer mpaRating){
		    String mpaRatingString = String.valueOf(mpaRating);
		    return repo.findByMpaRatingLike(mpaRatingString);		}
	
}
