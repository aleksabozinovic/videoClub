package com.videoclub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

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
	@Autowired private EntityManager entityManager;


	
		public List<Movie> listAllMovies(){
			return (List<Movie>) repo.findAll();
		}
	
		public void save(Movie movie) {
			repo.save(movie);
		}
		
		public Movie editMovie(Integer inventoryNumber){
			Optional<Movie> result = repo.findById(inventoryNumber);
			return result.get();			
		}

		public void deleteMovie(Integer inventoryNumber) {

			repo.deleteById(inventoryNumber);
			
		}
		

	public List<Movie> findMoviesByInventoryNumber(Integer inventoryNumber){
		String inventaryNumberString = String.valueOf(inventoryNumber);
		return repo.findByInventoryNumberLike(inventaryNumberString);
}

	public List<Movie> findByCriteria(
			String title, String genre, String language, Integer yearOfRecording, Integer inventoryNumber, Integer mpaRating
	){
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<Movie> cq = cb.createQuery(Movie.class);
			Root<Movie> movie = cq.from(Movie.class);

			List<Predicate> predicates = new ArrayList<>();

			if(title != null && !title.isEmpty())
				predicates.add(cb.like(movie.get("title"), title + "%"));
			if(genre != null && !genre.isEmpty())
				predicates.add(cb.like(movie.get("genre"), genre + "%"));
			if(language != null && !language.isEmpty())
				predicates.add(cb.like(movie.get("language"), language + "%"));
			if(yearOfRecording != null)
			    predicates.add(cb.like(cb.function("str", String.class, movie.get("yearOfRecording")), "%" + yearOfRecording + "%"));
			if(inventoryNumber != null)
			    predicates.add(cb.like(cb.function("str", String.class, movie.get("inventoryNumber")), "%" + inventoryNumber + "%"));
			if(mpaRating != null)
			    predicates.add(cb.like(cb.function("str", String.class, movie.get("mpaRating")), "%" + mpaRating + "%"));

			cq.select(movie).where(cb.and(predicates.toArray(new Predicate[0])));

			return entityManager.createQuery(cq).getResultList();
	}
	
}
