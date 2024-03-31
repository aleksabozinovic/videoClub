package com.videoclub.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.videoclub.movie.Movie;

@Repository
@Configurable
public interface MovieRepository extends JpaRepository<Movie, Integer>{
	List<Movie> findByTitle(String title);
	List<Movie> findByGenre(String genre);
	List<Movie> findByInventoryNumber(Integer inventoryNumber);
	List<Movie> findByLanguage(String language);
	List<Movie> findByYearOfRecording(String yearOfRecording);
	List<Movie> findByMpaRating(Integer mpaRating);

	// Trazi filmove koji nisu pozajmljeni
	List<Movie> findByInventoryNumberNotIn(List<Integer> inventoryNumber);
	
	@Query("SELECT m FROM Movie m WHERE CAST(m.mpaRating AS string) LIKE CONCAT(:mpaRating, '%')")
	List<Movie> findByMpaRatingLike(@Param("mpaRating") String mpaRating);
	
	@Query("SELECT m FROM Movie m WHERE CAST(m.inventoryNumber AS string) LIKE CONCAT(:inventoryNumber, '%')")
	List<Movie> findByInventoryNumberLike(@Param("inventoryNumber") String inventoryNumber);
}
