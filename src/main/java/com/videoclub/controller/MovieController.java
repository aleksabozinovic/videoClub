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
	public String filterMovie(@RequestParam("kriterijum") String kriterijum,
			@RequestParam("vrednost") String vrednost,  
			Model model) {
				List<Movie> filteredMovies;
				
				 switch (kriterijum) {
			        case "naslov":
			            filteredMovies = service.findMoviesByNaslov(vrednost);
			            break;
			        case "zanr":
			            filteredMovies = service.findMoviesByZanr(vrednost);
			            break;
			        case "inventarskiBroj":
			            filteredMovies = service.findMoviesByInventarskomBroju(Integer.parseInt(vrednost));
			            break;
			        case "jezik":
			            filteredMovies = service.findMoviesByJezik(vrednost);
			            break;
			        case "godinaSnimanja":
			            filteredMovies = service.findMoviesByGodinaSnimanja(vrednost);
			            break;
			        case "starosnoOgranicenje":
			            filteredMovies = service.findMoviesByMpaRating(vrednost);
			            break;
			        default:
			            filteredMovies = service.listAllMovies();
			            break;
			    }
				model.addAttribute("movieLists",filteredMovies);
				return "movie";
	}
	
	
}
