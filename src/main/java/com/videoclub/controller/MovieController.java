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
			@RequestParam("zanr") String zanr,
			@RequestParam("inventarskiBroj") String inventarskiBroj,
			@RequestParam("jezik") String jezik,
			@RequestParam("godinaSnimanja") String godinaSnimanja,
			@RequestParam("starosnoOgranicenje") String starosnoOgranicenje,
			Model model) {

			List<Movie> filteredMovie;

			if(naslov != null && !naslov.isEmpty()) {
				filteredMovie = service.findMoviesByNaslov(naslov);
			} else if (zanr != null && !zanr.isEmpty()) {
				filteredMovie = service.findMoviesByZanr(zanr);
	        } else if (inventarskiBroj != null && !inventarskiBroj.isEmpty()) {
	        	filteredMovie = service.findMoviesByInventarskomBroju(Integer.parseInt(inventarskiBroj));
	        } else if(jezik != null && !jezik.isEmpty()) {
	        	filteredMovie= service.findMoviesByJezik(jezik);   	
	        }  else if(godinaSnimanja != null && !godinaSnimanja.isEmpty()) {
	        	filteredMovie= service.findMoviesByGodinaSnimanja(godinaSnimanja);   	
	        } else if(starosnoOgranicenje != null && !starosnoOgranicenje.isEmpty()) {
	        	filteredMovie= service.findMoviesByMpaRating(Integer.parseInt(starosnoOgranicenje));   	
	        } 
	        else {
	        	filteredMovie = service.listAllMovies();
	        } {
			
				model.addAttribute("movieLists",filteredMovie);
				return "movie";
	}
	
	}	
}
