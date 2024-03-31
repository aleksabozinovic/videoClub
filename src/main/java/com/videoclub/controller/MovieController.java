package com.videoclub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.videoclub.movie.Movie;
import com.videoclub.service.MovieService;

@Controller
public class MovieController {
	@Autowired
	private MovieService service;
	
	@GetMapping("/movie")
	public String showDefaultMovie(Model model) {
		List<Movie> movieLists = service.listAllMovies();
		model.addAttribute("movieLists", movieLists);
		
		return "movie";
	}
	
	@GetMapping("/movie/addMovie")
	public String addingNewMovie(Model model) {
		model.addAttribute("movie", new Movie());
		return "addMovie";
	}
	
	@GetMapping("/movie/edit/{inventoryNumber}")
	public String editMovie(@PathVariable Integer inventoryNumber ,Model model) {
		Movie movie = service.editMovie(inventoryNumber);
		model.addAttribute("movie", movie);
		return "addMovie";
	}
	
	@PostMapping("/movie")
	public String saveMovie(Movie movie) {

		service.save(movie);
		return "redirect:/movie";
	}
	
	@GetMapping("/movie/delete/{inventoryNumber}")
	public String deleteMovie(@PathVariable Integer inventoryNumber) {
		service.deleteMovie(inventoryNumber);
		return "redirect:/movie";
	}
	
	@GetMapping("/filteredMovies")
	public String filterMovie(@RequestParam("title") String title,
			@RequestParam(value = "genre" , required = false) String genre,
			@RequestParam(value = "inventoryNumber" , required = false) Integer inventoryNumber,
			@RequestParam(value = "language" , required = false) String language,
			@RequestParam(value = "yearOfRecording" , required = false) Integer yearOfRecording,
			@RequestParam(value = "mpaRating" , required = false) Integer mpaRating,
			Model model) {

		List<Movie> filteredMovie = service.findByCriteria(title, genre, language, yearOfRecording, inventoryNumber, mpaRating);

		model.addAttribute("movieLists",filteredMovie);
		return "movie";
	}

}
