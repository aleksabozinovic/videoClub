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

	List<Movie> findByInventoryNumber(Integer inventoryNumber);

	@Query("SELECT m FROM Movie m WHERE CAST(m.inventoryNumber AS string) LIKE CONCAT(:inventoryNumber, '%')")
	List<Movie> findByInventoryNumberLike(@Param("inventoryNumber") String inventoryNumber);
}
