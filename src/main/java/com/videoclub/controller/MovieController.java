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
	
	@GetMapping("/movie/edit/{inventarskiBroj}")
	public String editMovie(@PathVariable Integer inventarskiBroj ,Model model) {
		Movie movie = service.editMovie(inventarskiBroj);
		model.addAttribute("movie", movie);
		return "addMovie";
	}
	
	@PostMapping("/movie")
	public String saveMovie(Movie movie) {

		service.save(movie);
		return "redirect:/movie";
	}
	
	@GetMapping("/movie/delete/{inventarskiBroj}")
	public String deleteMovie(@PathVariable Integer inventarskiBroj) {
		service.deleteMovie(inventarskiBroj);
		return "redirect:/movie";
	}
	
	@GetMapping("/filtriraniFilmovi")
	public String filterMovie(@RequestParam("naslov") String naslov,
			@RequestParam(value = "zanr" , required = false) String zanr,
			@RequestParam(value = "inventarskiBroj" , required = false) Integer inventarskiBroj,
			@RequestParam(value = "jezik" , required = false) String jezik,
			@RequestParam(value = "godinaSnimanja" , required = false) Integer godinaSnimanja,
			@RequestParam(value = "starosnoOgranicenje" , required = false) Integer starosnoOgranicenje,
			Model model) {

		List<Movie> filteredMovie = service.findByCriteria(naslov, zanr, jezik, godinaSnimanja, inventarskiBroj, starosnoOgranicenje);

		model.addAttribute("movieLists",filteredMovie);
		return "movie";
	}

}
